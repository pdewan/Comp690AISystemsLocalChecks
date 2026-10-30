package gradingTools.comp690f26.common;

/** Per-check grading units based on the released A1 rubric, used for new CLI checks.
 * A1 keeps its released annotations. Project roles use the two-point A1 extra-role
 * unit rather than reproducing the 34/18 rounding in its required-role suite.
 */
public final class GradingCheckWeights {
    private GradingCheckWeights() { }
    public static final int EVIDENCE_GATE = 0;
    public static final int OUTPUT_SYNTAX_SUITE = 20;
    public static final int SEPARATION_OF_CONCERNS_SUITE = 60;
    public static final int PROJECT_ROLE = 2;
    public static final int TRACE_EVENT = 2;
    public static final int SEPARATION = 5;
    public static final int REGISTRATION = 2;
    public static final int PHYSICAL_RELATIONSHIP = 2;
    public static final int COMMON_INTERFACE = 1;
    public static final int SINGLE_INTERFACE = 5;
    public static final int INTERFACE_FIELDS = 10;
    public static final int METHOD_CONTRACT = 2;
    public static final int PROPERTY_DESCRIPTION = 5;
    public static final int VALUE_CONSISTENCY = 5;
    public static final int STORE_CONSISTENCY = 5;
    public static final int COMMAND_RESULT = 15;
    public static final int ORDINARY_MESSAGE_RESULT = 10;
    public static final int SYSTEM_MESSAGE_RESULT = 5;
    public static final int COMMAND_RESULT_CONTAINMENT = 10;
    public static final int PARAMETER_VALUES = 20;
    public static final int ATTACHMENT_CONTENT = 10;
    public static final int OBSERVER_DELIVERY = 10;
    public static final int HISTORY_RETENTION = 10;
    public static final int REQUIRED_INPUT = 30;
    public static final int STREAM_SETTING_CONSISTENCY = 15;
}
