package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class HelpWindowTest extends ApplicationTest {

    private HelpWindow helpWindow;

    @Override
    public void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
    }

    @Test
    public void show_helpWindowIsDisplayedAndHandlesEscapeKey() {

        Platform.runLater(() -> {
            assertFalse(helpWindow.isShowing());
            helpWindow.show();
            assertTrue(helpWindow.isShowing());
        });

        sleep(500);
        Scene scene = helpWindow.getRoot().getScene();
        Platform.runLater(() -> scene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(
                javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false)));

        sleep(500);

        assertFalse(helpWindow.isShowing());
    }
}
