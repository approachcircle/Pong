package net.approachcircle.game.backend.tests;

import net.approachcircle.game.backend.DialogBox;
import net.approachcircle.game.backend.DialogType;
import net.approachcircle.game.backend.Screen;

public class DialogTestScreen extends Screen {
    DialogBox dialogBox;

    public DialogTestScreen() {
        dialogBox = new DialogBox(DialogType.Information, "dialog box with information");
    }
    @Override
    public void render() {
        dialogBox.render();
    }
}
