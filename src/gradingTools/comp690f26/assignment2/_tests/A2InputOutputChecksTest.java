package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.CLITraceInformation;
import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.common.io.*;
import gradingTools.comp690f26.files.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;


/** Independent stdout controls/mutations. No traces are supplied to the I/O
 * rules; real grading checks also run first in a directory without CLITrace.txt. */
public final class A2InputOutputChecksTest {
    private static int mutations;
    public static void main(String[] args) throws Exception {
        if(args.length!=2) throw new IllegalArgumentException("Supply correct directory and isolated test directory");
        Path correct=Path.of(args[0]), isolated=Path.of(args[1]);
        require(!correct.toAbsolutePath().normalize().equals(isolated.toAbsolutePath().normalize()),"Use a separate test directory");
        Files.createDirectories(isolated);
        var parser=F26Assignment2Suite.createInteractionsProcessor();
        var original=Files.readAllLines(correct.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME));
        var io=parser.processLines(original).interaction().orElseThrow();
        var simple=parser.processLines(simpleSession()).interaction().orElseThrow();
        for(var check:CLIInputOutputChecks.Check.values()) pass(check,simple,isolated);
        for(var kind:CLIOutputSyntax.Kind.values()) require(CLIOutputSyntax.check(kind,io).isEmpty(),"Correct syntax "+kind);
        for(var check:CLIInputOutputChecks.Check.values()) pass(check,io,correct);
        // Same behavior, different student values and the lecture's system label.
        var alternate=original.stream().map(s->s.replace("temperature = 0.4","temperature = 0.6")
            .replace("temperature to 0.4","temperature to 0.6").replace("temperature=0.4","temperature=0.6")
            .replace(".system] system_prompt = ",".system] system = ")
            .replace("conversation_id=A2","conversation_id=default").replace("conversation A2","conversation default")).toList();
        var alternateIO=parser.processLines(alternate).interaction().orElseThrow();
        for(var check:CLIInputOutputChecks.Check.values()) pass(check,alternateIO,correct);
        for(String mutation:List.of("wrong-end","bad-time","bad-seconds","bad-success","duplicate-echo")) {
            var lines=new ArrayList<>(original);
            if(mutation.equals("wrong-end")) replaceFirst(lines,s->s.startsWith("@@end"),s->s.replaceFirst("\\[\\d+]","[999]"));
            if(mutation.equals("bad-time")) replaceFirst(lines,s->s.startsWith("@@end"),s->s.replace("Time:","Elapsed:"));
            if(mutation.equals("bad-seconds")) replaceFirst(lines,s->s.startsWith("@@end"),s->s.replaceFirst("Time: [^)]*","Time: 00:00:99.000"));
            if(mutation.equals("bad-success")) replaceFirst(lines,s->s.startsWith("@@start"),s->s.replace("success=true","success=maybe"));
            if(mutation.equals("duplicate-echo")) replaceFirst(lines,s->s.startsWith("[3] /prop"),s->s.replace("[3]","[2]"));
            require(!parser.processLines(lines).succeeded(),"Outer mutation survived: "+mutation);mutations++;
        }
        for(var kind:CLIOutputSyntax.Kind.values()) {
            var changed=change(io,e->CLIOutputSyntax.selects(kind,e),s->"invalid body");
            require(!CLIOutputSyntax.check(kind,changed).isEmpty(),"Inner syntax mutation survived: "+kind);mutations++;
            require(!CLIOutputSyntax.check(kind,new CLIMixedIO(List.of())).isEmpty(),"Missing syntax evidence passed");mutations++;
        }
        for(var edit:List.<UnaryOperator<String>>of(s->s.replace("Messages:","Message:"),
                s->s.replace(".user]",".human]"),s->s.replace("prompt = ","prompt : "),
                s->s.replace(", metadata = ",", metadata : "),s->s.replace("integer={","integer={{"))) {
            var changed=change(io,e->CLIOutputSyntax.selects(CLIOutputSyntax.Kind.MESSAGES,e),edit);
            require(!CLIOutputSyntax.check(CLIOutputSyntax.Kind.MESSAGES,changed).isEmpty(),"Malformed message body accepted");mutations++;
        }
        var badGetter=change(io,e->e.input().kind()==CLIInteraction.Kind.PROPERTY_GET,s->s.replace("[source:","[origin:"));
        require(!CLIOutputSyntax.check(CLIOutputSyntax.Kind.PROPERTY,badGetter).isEmpty(),"Malformed getter source accepted");mutations++;
        for(var edit:List.<UnaryOperator<String>>of(s->s.replaceFirst("timeout = \\d+","timeout = 999"),
                s->s.replaceFirst("Set timeout to \\d+","Set timeout to 999"),
                s->s.replace("conversation A2","conversation wrong"))) {
            reject(CLIInputOutputChecks.Check.PROPERTIES,change(io,e->CLIOutputSyntax.selects(CLIOutputSyntax.Kind.PROPERTY,e),edit),correct);
        }
        Predicate<CLIMixedIO.Entry> transcript=e->CLICommandNames.TRANSCRIPT.equals(e.input().commandName());
        for(var edit:List.<UnaryOperator<String>>of(s->s.replaceFirst("\\[1]","[999]"),
                s->s+"\n[999] unexpected input",s->s.replaceFirst("(?m)^\\[1][^\\r\\n]*\\R",""),
                s->s.replace("@@[llava]","@@[wrong-model]"))) {
            reject(CLIInputOutputChecks.Check.TRANSCRIPT,change(io,transcript,edit),correct);
        }
        for(var check:List.of(CLIInputOutputChecks.Check.MESSAGES,CLIInputOutputChecks.Check.SYSTEM_MESSAGES)) {
            var view=check==CLIInputOutputChecks.Check.MESSAGES?CLIMessageView.ORDINARY:CLIMessageView.SYSTEM;
            Predicate<CLIMixedIO.Entry> selected=e->CLICommandNames.MESSAGES.equals(e.input().commandName()) && view.includes(e);
            for(var edit:List.<UnaryOperator<String>>of(s->s.replaceFirst("\\[15.user]","[999.user]"),
                    s->s.replace("timeout=300","timeout=999"),
                    s->s+"\n[999.user] prompt = extra, parameters = integer={timeout=300}",
                    s->s.replaceFirst("(?m)^\\[15.user][^\\r\\n]*\\R",""),
                    s->s.replaceFirst("response = [^\\r\\n]*", "response = wrong answer, metadata = integer={tokens=1}"))) {
                reject(check,change(io,selected,edit),correct);
            }
            reject(check,change(io,selected,s->s+"\n[999.system] system = unexpected"),correct);
        }
        for(var check:CLIInputOutputChecks.Check.values()) reject(check,new CLIMixedIO(List.of()),correct);
        // Availability and I/O checks run without the trace file.
        require(!Files.exists(isolated.resolve(CLITraceInformation.TRACE_FILE_NAME)),"Isolation directory must have no trace file");
        Files.copy(correct.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME),isolated.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME),StandardCopyOption.REPLACE_EXISTING);
        for(String file:List.of(F26Assignment2Suite.REQUIRED_IMAGE_ATTACHMENT,F26Assignment2Suite.REQUIRED_TEXT_ATTACHMENT))
            Files.copy(correct.resolve(file),isolated.resolve(file),StandardCopyOption.REPLACE_EXISTING);
        F26Assignment2Suite.setTraceDirectory(isolated.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        var root=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        for(var check:List.of(AdaptivePropertyOutputMatches.class,AdaptiveTranscriptOutputMatches.class,AdaptiveMessagesOutputMatches.class,AdaptiveSystemMessagesOutputMatches.class))
            require(JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(check).getLastResult().isPass(),"I/O-only grading failed: "+check);
        require(root.findTest(AdaptiveTraceFilesProvided.class).getFractionComplete()!=1,"I/O checks ran trace prerequisite");
        Files.writeString(isolated.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME),"Malformed but readable");
        require(new CLIInputOutputFileProvided().test(null,false).isPass(),"Availability parsed malformed file");
        require(!parser.process(isolated.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).succeeded(),"Malformed file passed syntax");
        System.out.println("A2InputOutputChecksTest passed: correct and alternate-value controls; "+mutations+" mutations; independent I/O grading and atomic availability");
        System.exit(0);
    }
    private static CLIMixedIO change(CLIMixedIO io,Predicate<CLIMixedIO.Entry> selected,UnaryOperator<String> edit) {
        boolean[] changed={false};
        var result=new CLIMixedIO(io.entries().stream().map(e->{
            if(!selected.test(e) || e.output().isEmpty()) return e;
            var o=e.output().get();String body=edit.apply(o.body());changed[0]|=!body.equals(o.body());
            return new CLIMixedIO.Entry(e.number(),e.input(),e.attachments(),Optional.of(new CLIMixedIO.Output(o.success(),o.conversation(),body,o.attributes())),e.chunks());
        }).toList());
        require(changed[0],"Mutation did not change any output");return result;
    }
    /** Two exchanges, no attachments, no parameter changes, bare model output,
     * and the lecture's system label. These choices are independent test data. */
    private static List<String> simpleSession() {
        List<String> lines=new ArrayList<>();
        block(lines,1,"/prop current_models = example-model","Set current_models to [example-model] for conversation default");
        block(lines,2,"/prop system_prompt = Be brief","Set system_prompt to Be brief for conversation default");
        block(lines,3,"/prop timeout = 7","Set timeout to 7 for conversation default");
        block(lines,4,"/prop timeout","timeout = 7 (whole-number) [source: conversation default]");
        block(lines,5,"First question","First answer");block(lines,6,"Second question","Second answer");
        block(lines,7,"/transcript","Transcript:\n[1] /prop current_models = example-model\n[1] Set current_models to [example-model] for conversation default"
            +"\n[2] /prop system_prompt = Be brief\n[2] Set system_prompt to Be brief for conversation default"
            +"\n[3] /prop timeout = 7\n[3] Set timeout to 7 for conversation default\n[4] /prop timeout"
            +"\n[5] First question\n[5] @@[example-model] First answer\n[6] Second question\n[6] @@[example-model] Second answer");
        String turns="[5.user] prompt = First question, parameters = integer={timeout=7}\n[5.assistant] response = First answer, metadata = integer={tokens=1}"
            +"\n[6.user] prompt = Second question, parameters = integer={timeout=7}\n[6.assistant] response = Second answer, metadata = integer={tokens=1}";
        block(lines,8,"/messages","Messages:\n"+turns);
        block(lines,9,"/messages -system","Messages:\n[system] system = Be brief\n"+turns);
        return lines;
    }
    private static void block(List<String> lines,int number,String input,String body) {
        lines.add("["+number+"] "+input);
        lines.add("@@start ["+number+"] success=true conversation_id=default");
        lines.addAll(body.lines().toList());
        lines.add("@@end ["+number+"] (Time: 00:00:00.001)");
    }
    private static void replaceFirst(List<String> lines,Predicate<String> select,UnaryOperator<String> edit) {
        for(int i=0;i<lines.size();i++) if(select.test(lines.get(i))) { lines.set(i,edit.apply(lines.get(i)));return; }
        throw new AssertionError("No mutation target");
    }
    private static void pass(CLIInputOutputChecks.Check check,CLIMixedIO io,Path dir)throws Exception {
        var errors=CLIInputOutputChecks.check(check,io,dir,F26Assignment2Suite.attachmentKinds());require(errors.isEmpty(),check+": "+errors);
    }
    private static void reject(CLIInputOutputChecks.Check check,CLIMixedIO io,Path dir)throws Exception {
        require(!CLIInputOutputChecks.check(check,io,dir,F26Assignment2Suite.attachmentKinds()).isEmpty(),"Mutation survived: "+check);mutations++;
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
