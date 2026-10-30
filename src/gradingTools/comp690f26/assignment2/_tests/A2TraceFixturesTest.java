package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.cli.CLICommandResultChecks;
import eduAI.trace.processors.cli.CLITraceExpectations;
import eduAI.trace.processors.mutations.CLICommandResultMutations;
import eduAI.trace.processors.mutations.CLIRequestInputMutations;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import org.junit.runners.Suite;


/** Uses the actual suite's trace checks, without GUI/project scans or model executions. */
public class A2TraceFixturesTest {
    private static CLICommandResultCheckerRegistry sessionRules(CLIMixedIO io, Path directory) throws java.io.IOException {
        var rules=F26Assignment2Suite.createCommandResultCheckers();
        rules.register(CLICommandNames.MESSAGES,new CLIMessagesCommandResultChecker(io,directory,F26Assignment2Suite.attachmentKinds()));
        return rules;
    }
    public static void main(String[] args) throws Exception {
        Path correct = Path.of(args[0]), broken = Path.of(args[1]);
        F26Assignment2Suite.configureProperties();
        Set<Class<?>> checks = new LinkedHashSet<>();
        collect(F26Assignment2Suite.class, checks);
        Method invoke = AbstractTraceProcessorCheck.class.getDeclaredMethod("check", TraceFilesProcessor.class);
        invoke.setAccessible(true);
        String baseline = failure(correct, checks, invoke);
        if (baseline != null) throw new AssertionError("Correct fixture rejected by " + baseline);
        byte[] io = Files.readAllBytes(correct.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME));
        List<String> report = new ArrayList<>(List.of("dataset\tresult\tfirst_rejecting_check"));
        int rejected = 0, unexpected = 0;
        for (String name : Files.readAllLines(broken.resolve("datasets.txt"))) {
            Path dataset = broken.resolve(name);
            if (!Arrays.equals(io, Files.readAllBytes(dataset.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME))))
                throw new AssertionError("Mutation changed the I/O log: " + name);
            if (Files.mismatch(correct.resolve(CLITraceInformation.TRACE_FILE_NAME),
                    dataset.resolve(CLITraceInformation.TRACE_FILE_NAME)) == -1)
                throw new AssertionError("No-op mutation: " + name);
            String result;
            try {
                result = failure(dataset, checks, invoke);
                if (result == null) { unexpected++; report.add(name + "\tSURVIVED\t"); }
                else { rejected++; report.add(name + "\tREJECTED\t" + result); }
            } catch (Exception e) {
                unexpected++; report.add(name + "\tERROR\t" + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            System.out.println(report.get(report.size() - 1));
        }
        Files.write(broken.resolve("validation.tsv"), report);
        System.out.println("Correct fixture passed " + checks.size() + " trace checks; rejected=" + rejected + " unexpected=" + unexpected);
        if (unexpected > 0) throw new AssertionError("Mutation validation has " + unexpected + " unexpected results");
    }

    private static void collect(Class<?> type, Set<Class<?>> checks) {
        var suite = type.getAnnotation(Suite.SuiteClasses.class);
        if (suite != null) for (Class<?> child : suite.value()) collect(child, checks);
        else if (AbstractTraceProcessorCheck.class.isAssignableFrom(type)) checks.add(type);
    }

    private static String failure(Path directory, Set<Class<?>> checks, Method invoke) throws Exception {
        var io = F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME));
        if (!io.succeeded()) throw new AssertionError("Unreadable fixture I/O: " + io.diagnostics());
        var processor = new NativeTraceFilesProcessor(directory.resolve(CLITraceInformation.TRACE_FILE_NAME));
        processor.processTraceFiles();
        Path commandExpectation=directory.resolve(eduAI.trace.processors.mutations.CLICommandConsistencyMutations.EXPECTATION_FILE);
        if(Files.exists(commandExpectation)) {
            var rule=eduAI.trace.processors.mutations.CLICommandConsistencyMutations.Check.valueOf(Files.readString(commandExpectation).strip());
            var records=processor.getTraceRecords().values();
            var result=switch(rule) {
                case REGISTRATION -> eduAI.trace.processors.cli.CLICommandHandlerChecks.check(records,eduAI.trace.processors.cli.CLICommandHandlerChecks.Check.REGISTRATION);
                case REUSE -> eduAI.trace.processors.cli.CLICommandHandlerChecks.check(records,eduAI.trace.processors.cli.CLICommandHandlerChecks.Check.REUSE);
                case PROPERTY_RESULT -> CLICommandResultChecks.checkTraces(records,F26Assignment2Suite.createCommandResultCheckers(),eduAI.trace.processors.cli.CLICommandNames.PROPERTY);
                case EXPECTED_INPUT -> F26Assignment2Suite.commandExpectations().check(records,io.interaction().orElseThrow());
            };
            if(result.passed()) throw new AssertionError("Command mutation survived intended check: "+directory+" "+rule);
            Files.writeString(directory.resolve("mutation-rejection.txt"),rule+"\n"+String.join("\n",result.level3ErrorMessages()));
        }
        Path choicesExpectation=directory.resolve(eduAI.trace.processors.mutations.CLIPropertyChoicesMutations.EXPECTATION_FILE);
        if(Files.exists(choicesExpectation)) {
            var rule=eduAI.trace.processors.cli.CLIPropertyChoicesChecks.Check.valueOf(Files.readString(choicesExpectation).strip());
            var result=eduAI.trace.processors.cli.CLIPropertyChoicesChecks.check(rule,processor.getTraceRecords().values(),F26Assignment2Suite.propertyExpectations());
            if(result.passed()) throw new AssertionError("Required-choices mutation survived intended check: "+directory+" "+rule);
            Files.writeString(directory.resolve("mutation-rejection.txt"),rule+"\n"+String.join("\n",result.level3ErrorMessages()));
        }
        Path propertyExpectation=directory.resolve(eduAI.trace.processors.mutations.CLIPropertyConsistencyMutations.EXPECTATION_FILE);
        if(Files.exists(propertyExpectation)) {
            var rule=eduAI.trace.processors.cli.CLIPropertyConsistencyChecks.Check.valueOf(Files.readString(propertyExpectation).strip());
            var result=eduAI.trace.processors.cli.CLIPropertyConsistencyChecks.check(rule,processor.getTraceRecords().values(),F26Assignment2Suite.createPropertyCheckers());
            if(result.passed()) throw new AssertionError("Property mutation survived intended check: "+directory+" "+rule);
            Files.writeString(directory.resolve("mutation-rejection.txt"),rule+"\n"+String.join("\n",result.level3ErrorMessages()));
        }
        processor.setExpectedContentChecks(new eduAI.trace.processors.cli.CLIExpectedContentChecks(CLITraceExpectations.from(io.interaction().orElseThrow(), directory)));
        Path sixCallExpectation = directory.resolve(eduAI.trace.processors.mutations.CLISixCallMutations.EXPECTATION_FILE);
        if (Files.exists(sixCallExpectation)) {
            String expected = Files.readString(sixCallExpectation).strip();
            var findings = eduAI.trace.processors.cli.CLISixCallChecks.findings(
                processor.getTraceRecords().values(), io.interaction().orElseThrow(), directory);
            if (findings.stream().noneMatch(f -> f.check().equals(expected)))
                throw new AssertionError("Six-call mutation missed " + expected + ": " + directory + " " + findings);
            Files.writeString(directory.resolve("mutation-rejection.txt"), findings.toString());
        } else if (directory.getFileName().toString().startsWith("six_call_")) {
            throw new AssertionError("Missing six-call mutation expectation: " + directory);
        }
        Path architectureExpectation = directory.resolve(eduAI.trace.processors.mutations.CLIArchitectureMutations.EXPECTATION_FILE);
        if (Files.exists(architectureExpectation)) {
            String[] expected = Files.readString(architectureExpectation).strip().split("\t", 2);
            var result = eduAI.trace.processors.cli.CLIArchitectureChecks.check(
                eduAI.trace.processors.cli.CLIArchitectureChecks.Check.valueOf(expected[0]),
                processor.getTraceRecords().values(), io.interaction().orElseThrow(), directory);
            if (result.passed() || result.level3ErrorMessages().stream().noneMatch(e -> e.contains(expected[1])))
                throw new AssertionError("Architecture mutation missed intended diagnostic: " + directory + " " + result);
            Files.writeString(directory.resolve("mutation-rejection.txt"), expected[0] + "\n"
                + String.join("\n", result.level3ErrorMessages()) + "\n");
        } else if (directory.getFileName().toString().startsWith("architecture_")) {
            throw new AssertionError("Missing architecture mutation expectation: " + directory);
        }
        Path requestExpectation = directory.resolve(CLIRequestInputMutations.EXPECTATION_FILE);
        if (Files.exists(requestExpectation)) {
            String[] expected = Files.readString(requestExpectation).strip().split("\t", 2);
            var result = CLITraceExpectations.from(io.interaction().orElseThrow(), directory).check(
                    processor.getTraceRecords().values(), null, CLITraceExpectations.Check.valueOf(expected[0]));
            if (result.passed() || result.level3ErrorMessages().stream().noneMatch(e -> e.contains(expected[1])))
                throw new AssertionError("Request mutation missed intended diagnostic: " + directory + " " + result);
            Files.writeString(directory.resolve("mutation-rejection.txt"), expected[0] + "\n"
                    + String.join("\n", result.level3ErrorMessages()) + "\n");
        } else if (directory.getFileName().toString().startsWith("request_input_")) {
            throw new AssertionError("Missing request mutation expectation: " + directory);
        }
        Path expectation = directory.resolve(CLICommandResultMutations.EXPECTATION_FILE);
        if (Files.exists(expectation)) {
            String[] expected = Files.readString(expectation).strip().split("\t", 2);
            if (expected.length != 2) throw new AssertionError("Invalid command mutation expectation: " + expectation);
            var result = CLICommandResultMutations.HANDLER_INSTANCES_CHECK.equals(expected[0])
                    ? eduAI.trace.processors.cli.CLICommandHandlerChecks.check(processor.getTraceRecords().values(),eduAI.trace.processors.cli.CLICommandHandlerChecks.Check.REUSE)
                    : java.util.Set.of(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,eduAI.trace.processors.cli.CLICommandNames.PROMPT).contains(expected[0])
                    ? CLICommandResultChecks.checkTraces(processor.getTraceRecords().values(),F26Assignment2Suite.createCommandResultCheckers(),expected[0])
                    : CLICommandResultChecks.check(processor.getTraceRecords().values(),
                            io.interaction().orElseThrow(), sessionRules(io.interaction().orElseThrow(), directory), expected[0]);
            if (result.passed() || result.level3ErrorMessages().stream().noneMatch(error -> error.contains(expected[1])))
                throw new AssertionError("Command mutation missed its intended diagnostic: " + directory + " expected="
                        + expected[1] + " actual=" + result);
            Files.writeString(directory.resolve("mutation-rejection.txt"),
                    "command=" + expected[0] + "\n" + String.join("\n", result.level3ErrorMessages()) + "\n");
        } else if (directory.getFileName().toString().startsWith("command_result_")) {
            throw new AssertionError("Command mutation has no intended-check expectation: " + directory);
        }
        if (directory.getFileName().toString().startsWith("property_set_")) {
            var setterResult = eduAI.trace.processors.cli.CLIPropertySetChecks.check(
                    processor.getTraceRecords().values(), io.interaction().orElseThrow().entries()
                            .stream().map(entry -> entry.input()).toList(), F26Assignment2Suite.createPropertyCheckers());
            if (setterResult.passed()) throw new AssertionError("Setter mutation survived its intended check: " + directory);
            Files.writeString(directory.resolve("mutation-rejection.txt"),
                    eduAI.trace.processors.cli.CLIPropertySetChecks.class.getSimpleName()+"\n" + String.join("\n", setterResult.level3ErrorMessages()) + "\n");
        }
        // The direct runner must supply the same parsed input prerequisite as the grading runner.
        CLIOuterOutputSyntax inputs = new CLIOuterOutputSyntax() {
            @Override public Path getInteractionsFile() { return directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME); }
            @Override public Optional<eduAI.trace.processors.cli.CLIMixedIO> getInteractions() {
                return io.interaction();
            }
        };
        Field prerequisites = PassFailJUnitTestCase.class.getDeclaredField("precedingTestInstances");
        prerequisites.setAccessible(true);
        for (Class<?> check : checks) {
            Object test = check.getConstructor().newInstance();
            prerequisites.set(test, List.of(inputs));
            TraceCheckResult result = (TraceCheckResult) invoke.invoke(test, processor);
            if (!result.passed()) return check.getSimpleName();
        }
        return null;
    }
}
