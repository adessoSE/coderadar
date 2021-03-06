package io.reflectoring.coderadar.analyzer.domain;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;

public enum MetricName {
  CODERADAR_SIZE_ELOC("coderadar:size:eloc:java", 0),
  CODERADAR_SIZE_SLOC("coderadar:size:sloc:java", 1),
  CODERADAR_SIZE_CLOC("coderadar:size:cloc:java", 2),
  CODERADAR_SIZE_LOC("coderadar:size:loc:java", 3),
  CHECKSTYLE_CUSTOM_IMPORT_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck", 4),
  CHECKSTYLE_TODO_COMMENT("checkstyle:com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck", 5),
  CHECKSTYLE_UNCOMMENTED_MAIN(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck", 6),
  CHECKSTYLE_ANNOTATION_LOCATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck", 7),
  CHECKSTYLE_ANNOTATION_USE_STYLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck", 8),
  CHECKSTYLE_AVOID_NESTED_BLOCKS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheck", 9),
  CHECKSTYLE_NEED_BRACES(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck", 10),
  CHECKSTYLE_RIGHT_CURLY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck", 11),
  CHECKSTYLE_ARRAY_TRAILING_COMMA(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck", 12),
  CHECKSTYLE_AVOID_INLINE_CONDITIONALS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.AvoidInlineConditionalsCheck", 13),
  CHECKSTYLE_DECLARATION_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck", 14),
  CHECKSTYLE_EMPTY_STATEMENT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck", 15),
  CHECKSTYLE_EQUALS_AVOID_NULL(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck", 16),
  CHECKSTYLE_EQUALS_HASH_CODE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck", 17),
  CHECKSTYLE_EXPLICIT_INITIALIZATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck", 18),
  CHECKSTYLE_FALL_THROUGH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck", 19),
  CHECKSTYLE_FINAL_LOCAL_VARIABLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck", 20),
  CHECKSTYLE_HIDDEN_FIELD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck", 21),
  CHECKSTYLE_ABBREVIATEION_AS_WORD_IN_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck", 22),
  CHECKSTYLE_ABSTRACT_CLASS_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck", 23),
  CHECKSTYLE_ANNOTATION_ON_SAME_LINE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck", 24),
  CHECKSTYLE_ANON_INNER_LENGTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheck", 25),
  CHECKSTYLE_ARRAY_TYPE_STYLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck", 26),
  CHECKSTYLE_AT_CLAUSE_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck", 27),
  CHECKSTYLE_AVOID_DOUBLE_BRACE_INITIALIZATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck",
      28),
  CHECKSTYLE_AVOID_ESCAPED_UNICODE_CHARATERS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck", 29),
  CHECKSTYLE_AVOID_NO_ARGUMENT_SUPER_CONSTRUCTOR_CALL(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck",
      30),
  CHECKSTYLE_AVOID_START_IMPORT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck", 31),
  CHECKSTYLE_AVOID_STATIC_IMPORT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck", 32),
  CHECKSTYLE_BOOLEAN_EXPRESSION_COMPLEXITY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck",
      33),
  CHECKSTYLE_CATCH_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck", 34),
  CHECKSTYLE_CLASS_DATA_ABSTRACTION_COUPLING(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck",
      35),
  CHECKSTYLE_CLASS_FAN_OUT_COMPLEXITY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck", 36),
  CHECKSTYLE_CLASS_MEMBER_IMPLIED_MODIFIER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.modifier.ClassMemberImpliedModifierCheck",
      37),
  CHECKSTYLE_CLASS_TYPE_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck", 38),
  CHECKSTYLE_COMMENTS_INDENTATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck", 39),
  CHECKSTYLE_CONSTANT_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck", 40),
  CHECKSTYLE_COVARIANT_EQUALS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.CovariantEqualsCheck", 41),
  CHECKSTYLE_CYCLOMATIC_COMPLEXITY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck", 42),
  CHECKSTYLE_DEFAULT_COMES_LAST(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck", 43),
  CHECKSTYLE_DESCEDANT_TOKEN(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.checks.DescendantTokenCheck", 44),
  CHECKSTYLE_EMPTY_BLOCK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck", 45),
  CHECKSTYLE_EMPTY_CATCH_BLOCK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck", 46),
  CHECKSTYLE_EMPTY_FOR_ITERATOR_PAD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck", 47),
  CHECKSTYLE_EMPTY_LINE_SEPARATOR(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck", 48),
  CHECKSTYLE_EXECUTABLE_STATEMENT_COUNT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck", 49),
  CHECKSTYLE_FILE_LENGTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck", 50),
  CHECKSTYLE_FILE_TAB_CHARACTER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck", 51),
  CHECKSTYLE_FINAL_CLASS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck", 52),
  CHECKSTYLE_FINAL_PARAMETERS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.checks.FinalParametersCheck", 53),
  CHECKSTYLE_GENERIC_WHITESPACE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck", 54),
  CHECKSTYLE_HEADER("checkstyle:com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck", 55),
  CHECKSTYLE_HIDE_UTILITY_CLASS_CONSTRUCTOR(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck",
      56),
  CHECKSTYLE_ILLEGAL_CATCH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck", 57),
  CHECKSTYLE_ILLEGAL_IDENTIFIER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.IllegalIdentifierNameCheck", 58),
  CHECKSTYLE_ILLEGAL_IMPORT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck", 59),
  CHECKSTYLE_ILLEGAL_INSTANTIATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck", 60),
  CHECKSTYLE_ILLEGAL_THROWS_CHECK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck", 61),
  CHECKSTYLE_ILLEGAL_TOKEN(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck", 62),
  CHECKSTYLE_ILLEGAL_TOKEN_TEXT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck", 63),
  CHECKSTYLE_ILLEGAL_TYPE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck", 64),
  CHECKSTYLE_IMPORT_CONTROL(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck", 65),
  CHECKSTYLE_IMPORT_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck", 66),
  CHECKSTYLE_INDENTATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck", 67),
  CHECKSTYLE_INNER_ASSIGNMENT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck", 68),
  CHECKSTYLE_INNER_TYPE_LAST(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck", 69),
  CHECKSTYLE_INTERFACE_IS_TYPE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.InterfaceIsTypeCheck", 70),
  CHECKSTYLE_INTERFACE_MEMBER_IMPLIED_MODIFIER(
      "checkstyle:com.puppycrawl.tools.checkstyle.modifier.coding.InterfaceMemberImpliedModifierCheck",
      71),
  CHECKSTYLE_INTERFACE_TYPE_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck",
      72),
  CHECKSTYLE_INVALID_JAVADOC_POSITION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck", 73),
  CHECKSTYLE_JAVADOC_BLOCK_TAG_LOCATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck", 74),
  CHECKSTYLE_JAVADOC_CONTENT_LOCATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck", 75),
  CHECKSTYLE_JAVADOC_METHOD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck", 76),
  CHECKSTYLE_JAVADOC_MISSING_LEADING_ASTERISK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck",
      77),
  CHECKSTYLE_JAVADOC_MISSING_WHITESPACE_AFTER_ASTERISK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck",
      78),
  CHECKSTYLE_JAVADOC_PACKAGE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck", 79),
  CHECKSTYLE_JAVADOC_PARAGRAPH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck", 80),
  CHECKSTYLE_JAVADOC_STYLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck", 81),
  CHECKSTYLE_JAVADOC_TAG_CONTINUATION_INDENTATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck",
      82),
  CHECKSTYLE_JAVADOC_TYPE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck", 83),
  CHECKSTYLE_JAVADOC_VARIABLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck", 84),
  CHECKSTYLE_JAVA_NCSS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck", 85),
  CHECKSTYLE_LAMBDA_BODY_LENGTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.LambdaBodyLengthCheck", 86),
  CHECKSTYLE_LAMBDA_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.LambdaParameterNameCheck", 87),
  CHECKSTYLE_LEFT_CURLY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck", 88),
  CHECKSTYLE_LINE_LENGTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck", 89),
  CHECKSTYLE_LOCAL_FINAL_VARIABLE_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck", 90),
  CHECKSTYLE_LOCAL_VARIABLE_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck", 91),
  CHECKSTYLE_MAGIC_NUMBER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck", 92),
  CHECKSTYLE_MATCH_XPATH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MatchXpathCheck", 93),
  CHECKSTYLE_MEMBER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck", 94),
  CHECKSTYLE_METHOD_COUNT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck", 95),
  CHECKSTYLE_METHOD_LENGTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck", 96),
  CHECKSTYLE_METHOD_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck", 97),
  CHECKSTYLE_METHOD_PARAM(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck", 98),
  CHECKSTYLE_METHOD_TYPE_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck", 99),
  CHECKSTYLE_MISSING_CTOR(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MissingCtorCheck", 100),
  CHECKSTYLE_MISSING_DEPRECATED(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck", 101),
  CHECKSTYLE_MISSING_JAVADOC_METHOD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck", 102),
  CHECKSTYLE_MISSING_JAVADOC_PACKAGE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocPackageCheck", 103),
  CHECKSTYLE_MISSING_JAVADOC_TYPE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck", 104),
  CHECKSTYLE_MISSING_OVERRIDE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck", 105),
  CHECKSTYLE_MISSING_SWITCH_DEFAULT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck", 106),
  CHECKSTYLE_MODIFIED_CONTROL_VARIABLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck", 107),
  CHECKSTYLE_MODIFIER_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck", 108),
  CHECKSTYLE_MULTIPLE_STRING_LITERALS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck", 109),
  CHECKSTYLE_MULTIPLEVARIABLE_DECLARATIONS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck",
      110),
  CHECKSTYLE_MUTABLE_EXCEPTION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck", 111),
  CHECKSTYLE_NESTED_FOR_DEPTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck", 112),
  CHECKSTYLE_NESTED_IF_DEPTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck", 113),
  CHECKSTYLE_NESTED_TRY_DEPTH(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck", 114),
  CHECKSTYLE_NEWLINE_AT_END_OF_FILE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck", 115),
  CHECKSTYLE_NO_ARRAY_TRAILING_COMMA(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NoArrayTrailingCommaCheck", 116),
  CHECKSTYLE_NO_CLONE("checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NoCloneCheck", 117),
  CHECKSTYLE_NO_CODE_IN_FILE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.NoCodeInFileCheck", 118),
  CHECKSTYLE_NO_FINALIZER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck", 119),
  CHECKSTYLE_NO_LINE_WRAP(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck", 120),
  CHECKSTYLE_NON_EMPTY_AT_CLAUSE_DESCRIPTION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck",
      121),
  CHECKSTYLE_NO_ENUM_TRAILING_COMMA(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.NoEnumTrailingCommaCheck", 122),
  CHECKSTYLE_NO_WHITESPACE_AFTER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck", 123),
  CHECKSTYLE_NO_WHITESPACE_BEFORE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck", 124),
  CHECKSTYLE_NPATH_COMPLEXITY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck", 125),
  CHECKSTYLE_ONE_STATEMENT_PER_LINE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck", 126),
  CHECKSTYLE_ONE_TOP_LEVEL_CLASS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck", 127),
  CHECKSTYLE_OPERATOR_WRAP(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck", 128),
  CHECKSTYLE_ORDERED_PROPERTIES(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.OrderedPropertiesCheck", 129),
  CHECKSTYLE_OUTER_TYPE_FILENAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck", 130),
  CHECKSTYLE_OUTER_TYPE_NUMBER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck", 131),
  CHECKSTYLE_OVERLOAD_METHODS_DECLARATION_ORDER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck",
      132),
  CHECKSTYLE_PACKAGE_ANNOTATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.PackageAnnotationCheck", 133),
  CHECKSTYLE_PACKAGE_DECLARATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck", 134),
  CHECKSTYLE_PACKAGE_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck", 135),
  CHECKSTYLE_PARAMETER_ASSIGNMENT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ParameterAssignmentCheck", 136),
  CHECKSTYLE_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck", 137),
  CHECKSTYLE_PARAMETER_NUMBER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck", 138),
  CHECKSTYLE_PAREN_PAD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck", 139),
  CHECKSTYLE_PATTERN_VARIABLE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck", 140),
  CHECKSTYLE_RECORD_COMPONENT_NUMBER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.sizing.RecordComponentNumberCheck", 141),
  CHECKSTYLE_RECORD_COMPONENT_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.RecordComponentNameCheck", 142),
  CHECKSTYLE_RECORD_TYPE_PARAMETER_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.RecordTypeParameterNameCheck", 143),
  CHECKSTYLE_REDUNDANT_IMPORT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck", 144),
  CHECKSTYLE_REDUNDANT_MODIFIER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck", 145),
  CHECKSTYLE_REGEXP("checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck", 146),
  CHECKSTYLE_REGEXP_HEADER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck", 147),
  CHECKSTYLE_REGEXP_MULTILINE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck", 148),
  CHECKSTYLE_REGEXP_ON_FILENAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck", 149),
  CHECKSTYLE_REGEXP_SINGLELINE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck", 150),
  CHECKSTYLE_REGEXP_SINGLELINE_JAVA(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck", 151),
  CHECKSTYLE_REQUIRE_THIS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck", 152),
  CHECKSTYLE_RETURN_COUNT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck", 153),
  CHECKSTYLE_SEPARATOR_WRAP(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck", 154),
  CHECKSTYLE_SIMPLIFY_BOOLEAN_EXPRESSION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck",
      155),
  CHECKSTYLE_SIMPLIFY_BOOLEAN_RETURN(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanReturnCheck", 156),
  CHECKSTYLE_SINGLE_LINE_JAVADOC(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck", 157),
  CHECKSTYLE_REQUIRE_EMPTY_LINE_BEFORE_BLOCK_TAG_GROUP(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck",
      158),
  CHECKSTYLE_SINGLE_SPACE_SEPARATOR(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck",
      159),
  CHECKSTYLE_STATIC_VARIABLE_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck", 160),
  CHECKSTYLE_STRING_LITERAL_EQUALITY(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.StringLiteralEqualityCheck", 161),
  CHECKSTYLE_SUMMARY_JAVADOC(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck", 162),
  CHECKSTYLE_SUPER_CLONE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.SuperCloneCheck", 163),
  CHECKSTYLE_SUPER_FINALIZE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheck", 164),
  CHECKSTYLE_SUPPRESS_WARNINGS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck", 165),
  CHECKSTYLE_THROWS_COUNT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck", 166),
  CHECKSTYLE_TRAILING_COMMENT(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.TrailingCommentCheck", 167),
  CHECKSTYLE_TRANSLATION_CHECK(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.TranslationCheck", 168),
  CHECKSTYLE_TYPECAST_PAREN_PAD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck", 169),
  CHECKSTYLE_TYPE_NAME(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck", 170),
  CHECKSTYLE_UNIQUE_PROPERTIES(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck", 171),
  CHECKSTYLE_UNNECESSARY_PARENTHESES(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck", 172),
  CHECKSTYLE_UNNECESSARY_SEMICOLON_ENUMERATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInEnumerationCheck",
      173),
  CHECKSTYLE_UNNECESSARY_SEMICOLON_IN_TRY_WITH_RESOURCES(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInTryWithResourcesCheck",
      174),
  CHECKSTYLE_UNNECESSARY_SEMICOLON_AFTER_OUTER_TYPE_DECLARATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
      175),
  CHECKSTYLE_UNNECESSARY_SEMICOLON_AFTER_TYPE_MEMBER_DECLARATION(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
      176),
  CHECKSTYLE_UNUSED_IMPORTS(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck", 177),
  CHECKSTYLE_UPPER_ELL("checkstyle:com.puppycrawl.tools.checkstyle.checks.UpperEllCheck", 178),
  CHECKSTYLE_VARIABLE_DECLARATION_USAGE_DISTANCE(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck",
      179),
  CHECKSTYLE_VISIBILITY_MODIFIER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck", 180),
  CHECKSTYLE_WHITESPACE_AFTER(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck", 181),
  CHECKSTYLE_WHITESPACE_AROUND(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck", 182),
  CHECKSTYLE_WRITE_TAG(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck", 183),
  CHECKSTYLE_EMPTY_FOR_INITIALIZER_PAD(
      "checkstyle:com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck",
      184);

  private final String name;
  private final int integerValue;

  private static final Map<String, MetricName> lookupMap =
      Maps.newHashMapWithExpectedSize(MetricName.values().length);
  private static final MetricName[] lookupArray = new MetricName[MetricName.values().length];

  static {
    for (MetricName m : EnumSet.allOf(MetricName.class)) {
      lookupMap.put(m.name, m);
      lookupArray[m.integerValue] = m;
    }
  }

  MetricName(String name, int i) {
    this.name = name;
    this.integerValue = i;
  }

  public String getName() {
    return name;
  }

  public int getIntegerValue() {
    return integerValue;
  }

  public static MetricName valueOfString(String metricName) {
    return lookupMap.get(metricName);
  }

  public static MetricName valueOfInt(int metricInteger) {
    return lookupArray[metricInteger];
  }
}
