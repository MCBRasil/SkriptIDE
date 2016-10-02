package com.skriptide.codemanage;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import org.fxmisc.richtext.CodeArea;

/**
 * Created by yannh on 25.09.2016.
 */
public class AutoComplete {

    public void setAutoComplete(CodeArea area, CompleteList completeList, TabPane codeTabPane, Button commandSendBtn) {

        area.setOnKeyPressed(event -> {

            KeyCode code = event.getCode();
            if (event.isShiftDown()) {
                if (code == KeyCode.DIGIT8) {

                    javafx.application.Platform.runLater(() -> {

                        area.replaceText(area.getCaretPosition(), area.getCaretPosition(), area.getText(area.getCaretPosition(), area.getCaretPosition()) + ")");
                        area.moveTo(area.getCaretPosition() - 1);
                    });

                }
                if (code == KeyCode.DIGIT2) {

                    javafx.application.Platform.runLater(() -> {

                        area.replaceText(area.getCaretPosition(), area.getCaretPosition(), area.getText(area.getCaretPosition(), area.getCaretPosition()) + "\"");
                        area.moveTo(area.getCaretPosition() - 1);
                    });
                }

            }
            if (event.isShortcutDown()) {
                if (code == KeyCode.DIGIT7) {


                    javafx.application.Platform.runLater(() -> {

                        area.replaceText(area.getCaretPosition(), area.getCaretPosition(), area.getText(area.getCaretPosition(), area.getCaretPosition()) + "}");
                        area.moveTo(area.getCaretPosition() - 1);
                    });

                }
                if (code == KeyCode.DIGIT8) {

                    javafx.application.Platform.runLater(() -> {

                        area.replaceText(area.getCaretPosition(), area.getCaretPosition(), area.getText(area.getCaretPosition(), area.getCaretPosition()) + "]");
                        area.moveTo(area.getCaretPosition() - 1);
                    });
                }


            } else if (isChar(code) && !(event.isAltDown()) && !(event.isControlDown()) && !(event.isShortcutDown())) {
                completeList.chooseList(codeTabPane, commandSendBtn);
            }


        });
    }

    private boolean isChar(KeyCode code) {
        return code.isLetterKey() || code.isDigitKey();
    }


}
