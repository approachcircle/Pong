package net.approachcircle.game.backend.tests;

import net.approachcircle.game.backend.DialogBox;
import net.approachcircle.game.backend.DialogListenerAdapter;
import net.approachcircle.game.backend.DialogType;
import net.approachcircle.game.backend.Screen;

public class DialogTestScreen extends Screen {
    DialogBox dialogBox;
    DialogBox dialogBox2;

    public DialogTestScreen() {
        dialogBox = new DialogBox(DialogType.Information, "dialog box with information", new DialogListenerAdapter() {
            @Override
            public void onOk() {
                dialogBox.toggle();
                dialogBox2.toggle();
            }
        });
        dialogBox2 = new DialogBox(DialogType.Question, "do you wish to close this?", new DialogListenerAdapter() {
            @Override
            public void onYes() {
                dialogBox2.toggle();
            }
        });
        dialogBox.toggle();
    }
    @Override
    public void render() {
        dialogBox.render();
        dialogBox2.render();
    }
}
