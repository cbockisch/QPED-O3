package eu.qped.java.checkers.style;


import eu.qped.framework.feedback.defaultfeedback.DefaultFeedback;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackDirectoryProvider;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackParser;
import eu.qped.framework.feedback.hint.HintType;
import eu.qped.java.checkers.style.reportModel.StyleCheckReport;
import eu.qped.java.utils.FileExtensions;
import eu.qped.java.utils.SupportedLanguages;
import lombok.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static eu.qped.java.utils.markdown.MarkdownFormatterUtility.*;

@AllArgsConstructor
@Builder
@Data
public class StyleFeedbackGenerator {


    private final static String NEWLINE = "\n\n";

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private Map<String, String[]> feedbacks;

    private StyleCheckReport report;

    private DefaultFeedbackParser defaultFeedbackParser;

    private String language;

    // TODO: migrate to json @Omar
    private void setUpData() {
        feedbacks = new HashMap<>();
        feedbacks.put("UnnecessaryLocalBeforeReturn", new String[]{"", ""});
        feedbacks.put("CommentRequired",
                new String[]{"", ""});
        feedbacks.put("NoPackage",
                new String[]{"Feedback for CommentRequired", ""});
        feedbacks.put("MethodNamingConventions",
                new String[]{"Your method name does not match the rules that are given." + NEWLINE +
                        "Java code conventions recommend: any Method should start with a small letter and the name should describe the function.",
                        "example: run() , canRun() ,..."});
        feedbacks.put("VariableNamingConventions",
                new String[]{"Java code conventions recommend: any variable/Field name should start with a small letter and the name should be clear and in english.",
                        "example: String color;"});
        feedbacks.put("FieldNamingConventions", new String[]{"Your field name does not match the rules that are given" + NEWLINE +
                "Java code conventions recommend: any variable/Field name should start with a small letter and the name should be clear and in english.",
                "example: String color;"});
        feedbacks.put("LocalVariableNamingConventions", new String[]{"Your local variable name does not match the rules that are given" + NEWLINE +
                "Java code conventions recommend: any variable name should start with a small letter and the name should be clear and in english.",
                "example: String color;"});
        feedbacks.put("IfStmtsMustUseBraces",
                new String[]{"Programming at its finest: every java control Block should have braces!",
                        "like: if (condition) {consequence}"});
        feedbacks.put("WhileLoopMustUseBraces",
                new String[]{"Programming at its finest: every java control Block should have braces!",
                        "like: while (condition) {Loop}"});
        feedbacks.put("ForLoopMustUseBraces",
                new String[]{"Programming at its finest: every java control Block should have braces!",
                        "like: for (condition) {Loop}"});
        feedbacks.put("BooleanGetMethodName",
                new String[]{"Your boolean method name does not match the rules that are given" + NEWLINE +
                        "Java code conventions recommend: boolean methods name should start with the verbs can or is.",
                        "like: canRun() , isBlue() , etc..."});
        feedbacks.put("ClassNamingConventions", new String[]{"Your class name does not match the rules that are given" + NEWLINE +
                "Java code conventions recommend: every java class name should start with a capital letter and follow the camel case conventions."
                , "example: class MyClass {//code}"});
        feedbacks.put("shortMethodName",
                new String[]{"Your Method name is too short for the rules that are given." + NEWLINE
                        + "Java code conventions recommend: any method name should be in english and mind. 3 letters.", ""});
        feedbacks.put("AbstractClassWithoutAnyMethod", new String[]{"", ""});
        feedbacks.put("LogicInversion", new String[]{"", ""});

        feedbacks.put("SimplifyBooleanReturns", new String[]{"some boolean expressions can be simplified, especially when you are writing a return-statement",
                "example:From: boolean canRun(int speed) {" + NEWLINE +
                        "boolean var= speed> 5;" + NEWLINE +
                        "return var;" + NEWLINE +
                        "To: return speed > 5;"
        });
        feedbacks.put("SimplifyBooleanExpressions", new String[]{"", ""});
        feedbacks.put("CommentContent", new String[]{"", ""});
        feedbacks.put("CommentSize", new String[]{"", ""});
        feedbacks.put("UncommentedEmptyConstructor", new String[]{"", ""});
        feedbacks.put("UncommentedEmptyMethodBody", new String[]{"", ""});
        feedbacks.put("ShortVariable", new String[]{"You should name your variable clear and in english", ""});
        feedbacks.put("AvoidFieldNameMatchingMethodName", new String[]{"", ""});
        feedbacks.put("AvoidFieldNameMatchingTypeName", new String[]{"", ""});
        feedbacks.put("AvoidMultipleUnaryOperators", new String[]{"", ""});
        feedbacks.put("DontUseFloatTypeForLoopIndices", new String[]{"", ""});
        feedbacks.put("EmptyIfStmt", new String[]{"", ""});
        feedbacks.put("EmptyStatementBlock", new String[]{"", ""});
        feedbacks.put("EmptyStatementNotInLoop", new String[]{"", ""});
        feedbacks.put("EmptySwitchStatements", new String[]{"", ""});
        feedbacks.put("EmptyTryBlock", new String[]{"", ""});
        feedbacks.put("EmptyWhileStmt", new String[]{"", ""});
        feedbacks.put("EqualsNull", new String[]{"", ""});
        feedbacks.put("IdempotentOperations", new String[]{"", ""});
        feedbacks.put("JumbledIncrementer", new String[]{"", ""});
        feedbacks.put("MethodWithSameNameAsEnclosingClass", new String[]{"", ""});
        feedbacks.put("MisplacedNullCheck", new String[]{"", ""});
        feedbacks.put("UnconditionalIfStatement", new String[]{"", ""});
        feedbacks.put("OnlyOneReturn", new String[]{"it's easy to write [if( condition ) return true; else return false] as [return condition]", ""});
    }

    /**
     * @return map of filenames as keys and a collection of feedbacks for key file.
     */
    public Map<String, List<StyleFeedback>> generateFeedbacks() {

        setUpData();
        List<StyleFeedback> resultList = new ArrayList<>();
        var defaultFeedbacksByTechnicalCause = getDefaultFeedbacksByTechnicalCause();
        report.getFileEntries()
                .forEach(
                        reportFileEntry -> reportFileEntry
                                .getViolations()
                                .forEach(
                                        violation -> {
                                            File temp = new File(reportFileEntry.getFileName());
                                            resultList.add(
                                                    StyleFeedback.builder()
                                                            .file(
//                                                                    temp.getParentFile().getName() + "." +
                                                                    temp.getName()
                                                            )
                                                            .line(
                                                                    String.valueOf(
                                                                            (temp.getName().contains("TestClass"))
                                                                                    ? violation.getBeginLine() - 3
                                                                                    : violation.getBeginLine()
                                                                    )
                                                            )
                                                            .content(
                                                                    (defaultFeedbacksByTechnicalCause.containsKey(violation.getRule())) ?
                                                                            Arrays.stream(defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getReadableCause().split(NEW_LINE))
                                                                                    .map(String::trim)
                                                                                    .collect(Collectors.joining(NEW_LINE))
                                                                            : violation.getDescription()
                                                            )
                                                            .desc(violation.getDescription())
                                                            .rule(violation.getRule())
                                                            .example(
                                                                    (defaultFeedbacksByTechnicalCause.containsKey(violation.getRule())
                                                                            && defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getHints() != null
                                                                            && !defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getHints().isEmpty()
                                                                    ) ?
                                                                            (defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getHints().get(0).getType().equals(HintType.CODE_EXAMPLE) ?
                                                                                    asCodeBlock(defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getHints().get(0).getContent(), CODE_JAVA)
                                                                                    : Arrays.stream(defaultFeedbacksByTechnicalCause.get(violation.getRule()).get(0).getHints().get(0).getContent().split(NEW_LINE))
                                                                                    .map(String::trim)
                                                                                    .collect(Collectors.joining(NEW_LINE))
                                                                            )
                                                                            : ""
//                                                                            getFeedbackExample(violation.getRule())
                                                            )
                                                            .build()
                                            );
                                        }
                                )
                );

        return resultList.stream().collect(Collectors.groupingBy(
                StyleFeedback::getFile
        ));
    }

    private Map<String, List<DefaultFeedback>> getDefaultFeedbacksByTechnicalCause() {
        var dirPath = DefaultFeedbackDirectoryProvider.provideDefaultFeedbackDirectory(StyleChecker.class);
        if (defaultFeedbackParser == null) {
            defaultFeedbackParser = new DefaultFeedbackParser();
        }
        if (language == null || language.equals("")) setLanguage(SupportedLanguages.ENGLISH);
        var allDefaultFeedbacks = defaultFeedbackParser.parse(dirPath, language + FileExtensions.JSON);
        return allDefaultFeedbacks.stream().collect(Collectors.groupingBy(DefaultFeedback::getTechnicalCause));
    }


    private String getFeedbackBody(String rule) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String[]> entry : feedbacks.entrySet()) {
            if (entry.getKey().equals(rule)) {
                result.append(entry.getValue()[0]);
            }
        }
        return result.toString();
    }

    private String getFeedbackExample(String rule) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String[]> entry : feedbacks.entrySet()) {
            if (entry.getKey().equals(rule)) {
                result.append(entry.getValue()[1]);
            }
        }
        return result.toString();
    }


}
