package io.reflectoring.coderadar.analyzer.domain;

public enum MetricName {
    CODERADAR_SIZE_ELOC("coderadar:size:eloc:java", 1),
    CODERADAR_SIZE_SLOC("coderadar:size:sloc:java", 2),
    CODERADAR_SIZE_CLOC("coderadar:size:cloc:java", 3),
    CODERADAR_SIZE_LOC("coderadar:size:loc:java", 4),
    CHECKSTYLE_CUSTOM_IMPORT_ORDER_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck", 5),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.TodoCommentCheck", 6),
    CHECKSTYLE_UNCOMMENTED_MAIN_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck", 7),
    CHECKSTYLE_ANNOTATION_LOCATION_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck", 8),
    CHECKSTYLE_ANNOTATION_USE_STYLE_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck", 9),
    CHECKSTYLE_AVOID_NESTED_BLOCKS_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheck", 10),
    CHECKSTYLE_NEED_BRACES_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck", 11);
/*    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.RightCurlyCheck", 11),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ArrayTrailingCommaCheck", 12),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.AvoidInlineConditionalsCheck", 13),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.DeclarationOrderCheck", 14),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.EmptyStatementCheck", 15),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.EqualsAvoidNullCheck", 16),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.EqualsHashCodeCheck", 17),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ExplicitInitializationCheck", 18),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.FallThroughCheck", 19),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.FinalLocalVariableCheck", 20),
    CHECKSTYLE_TODO_COMMENT_CHECK("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.HiddenFieldCheck", 21);*/

    private final String name;
    private final int integerValue;

    MetricName(String name, int i) {
        this.name = name;
        integerValue = i;
    }

    public String getName() {
        return name;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public static MetricName valueOfString(String metricName) {
        for (MetricName e : values()) {
            if (e.name.equals(metricName)) {
                return e;
            }
        }
        throw new AssertionError();
    }

    public static MetricName valueOfInt(int metricInteger) {
        for (MetricName e : values()) {
            if (e.integerValue == metricInteger) {
                return e;
            }
        }
        throw new AssertionError();
    }
}
