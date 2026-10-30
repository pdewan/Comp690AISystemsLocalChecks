package gradingTools.comp690f26.assignment1.projectClassesSuite.extra;

import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.annotations.Explanation;
import util.annotations.IsExtra;
import util.annotations.MaxValue;
import util.trace.Tracer;

@Explanation("Resolves optional merger roles to project classes; untraced roles do not veto the global gate.")
@RunWith(Suite.class)

@Suite.SuiteClasses({
    StreamChunkMergerExistsInProject.class,
    StreamChunkMergerFactoryExistsInProject.class,
    StreamChunkMergerFactoryRegistryExistsInProject.class,
})
@MaxValue(6)
@IsExtra(true)
public class A1ProjectExtraClassesSuite {
	 
	
}
