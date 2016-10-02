package com.skriptide.codemanage;

import com.skriptide.main.Main;
import com.skriptide.util.skunityapi.*;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liz3ga on 27.07.2016.
 */
public class ControlMain {


    private static final SkUnityAPI skUnity = new SkUnityAPI();


    public static void controlCode(CodeArea code) {


        String saver = code.getText();


        code.clear();
        code.setParagraphGraphicFactory(LineNumberFactory.get(code));

        code.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    code.setStyleSpans(0, computeHighlighting(code.getText()));
                });
        code.replaceText(0, 0, saver);
        if (Main.debugMode) {
            System.out.println("Highlighted : " + code.getText());
        }
    }


    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("CONDITIONS") != null ? "conditions" :
                            matcher.group("EFFECTS") != null ? "effects" :
                                    matcher.group("EVENTS") != null ? "events" :
                                            matcher.group("EXPRESSIONS") != null ? "expressions" :
                                                    matcher.group("TYPES") != null ? "types" :
                                                            matcher.group("SUPERS") != null ? "supers" :
                                                                    matcher.group("PAREN") != null ? "paren" :
                                                                            matcher.group("BRACE") != null ? "brace" :
                                                                                    matcher.group("BRACKET") != null ? "bracket" :
                                                                                            matcher.group("SEMICOLON") != null ? "semicolon" :
                                                                                                    matcher.group("STRING") != null ? "string" :
                                                                                                            matcher.group("COMMENT") != null ? "comment" :

                                                                                                                    null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();


        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static final String[] CONDITIONS = getCons();
    private static final String[] EFFECTS = getEffects();
    private static final String[] EVENTS = getEvents();
    private static final String[] EXPRESSIONS = getExpressions();
    private static final String[] TYPES = getTypes();
    private static final String[] SUPERS = getSupers();
    private static final String CONDITIONS_PATTERN = "\\b(" + String.join("|", CONDITIONS) + ")\\b";
    private static final String EFFECTS_PATTERN = "\\b(" + String.join("|", EFFECTS) + ")\\b";
    private static final String EVENTS_PATTERN = "\\b(" + String.join("|", EVENTS) + ")\\b";
    private static final String EXPRESSIONS_PATTERN = "\\b(" + String.join("|", EXPRESSIONS) + ")\\b";
    private static final String TYPES_PATTERN = "\\b(" + String.join("|", TYPES) + ")\\b";
    private static final String SUPERS_PATTERN = "\\b(" + String.join("|", SUPERS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "#[^\n]*";


    private static final Pattern PATTERN = Pattern.compile(
            "(?<CONDITIONS>" + CONDITIONS_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<EFFECTS>" + EFFECTS_PATTERN + ")"
                    + "|(?<EVENTS>" + EVENTS_PATTERN + ")"
                    + "|(?<EXPRESSIONS>" + EXPRESSIONS_PATTERN + ")"
                    + "|(?<TYPES>" + TYPES_PATTERN + ")"
                    + "|(?<SUPERS>" + SUPERS_PATTERN + ")"
            , Pattern.CASE_INSENSITIVE);

    private static String[] getCons() {

        String[] cnsArray;
        ArrayList<ApiCondition> conditions = skUnity.getConditions();
        cnsArray = new String[conditions.size()];
        for (int i = 0; i != conditions.size(); i++) {
            ApiCondition condition = conditions.get(i);
            if (condition.getId() != null && !Objects.equals(condition.getId(), "")) {
                cnsArray[i] = condition.getId().trim();

            }

        }
        return cnsArray;
    }

    private static String[] getEffects() {

        String[] cnsArray;
        ArrayList<ApiEffect> effects = skUnity.getEffects();
        cnsArray = new String[effects.size()];
        for (int i = 0; i != effects.size(); i++) {
            ApiEffect condition = effects.get(i);
            if (condition.getId() != null && !Objects.equals(condition.getId(), "")) {
                cnsArray[i] = condition.getId().trim();

            }

        }
        return cnsArray;
    }

    private static String[] getEvents() {

        String[] cnsArray;
        ArrayList<ApiEvent> events = skUnity.getEvents();
        cnsArray = new String[events.size()];
        for (int i = 0; i != events.size(); i++) {
            ApiEvent condition = events.get(i);
            if (condition.getId() != null && !Objects.equals(condition.getId(), "")) {
                cnsArray[i] = condition.getId().trim();

            }

        }
        return cnsArray;
    }

    private static String[] getExpressions() {

        String[] cnsArray;
        ArrayList<ApiExpression> expressions = skUnity.getExpressions();
        cnsArray = new String[expressions.size()];
        for (int i = 0; i != expressions.size(); i++) {
            ApiExpression condition = expressions.get(i);
            if (condition.getId() != null && !Objects.equals(condition.getId(), "")) {
                cnsArray[i] = condition.getId().trim();

            }

        }
        return cnsArray;
    }

    private static String[] getTypes() {

        String[] cnsArray;
        ArrayList<ApiType> types = skUnity.getTypes();
        cnsArray = new String[types.size()];
        for (int i = 0; i != types.size(); i++) {
            ApiType condition = types.get(i);
            if (condition.getId() != null && !Objects.equals(condition.getId(), "")) {
                cnsArray[i] = condition.getId().trim();

            }

        }
        return cnsArray;
    }

    private static String[] getSupers() {

        Supers supers = new Supers();

        return supers.getSupervArray();
    }

}
