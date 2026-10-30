package gradingTools.comp690f26.common.properties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/**
 * Groups property type, choices, values and accumulated store contents.
 * Matching values pass; inconsistent labels, choices or stored values fail.
 * Registry, handler and store identities belong to structural suites.
 * Missing evidence earns no credit in the affected leaf check.
 */
@Explanation("Checks property result meanings, alternatives, getter/setter agreement and stored values.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    PropertyResultTypesConsistent.class,
    PropertyAlternativesConsistent.class,
    BooleanPropertyAlternatives.class,
    RequiredModelAlternatives.class,
    PropertyGettersMatchSetters.class,
    PropertyStoreConsistent.class
})
public class A2PropertySemanticsSuite { }
