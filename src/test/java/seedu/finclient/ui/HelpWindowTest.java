package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class HelpWindowTest extends ApplicationTest {

    private HelpWindow helpWindow;

    @BeforeAll
    public static void initJavaFX() {
        Platform.startup(() -> {});
    }

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> helpWindow = new HelpWindow(stage));
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void show_helpWindowIsDisplayedAndHandlesEscapeKey() {
        interact(() -> {
            assertFalse(helpWindow.isShowing());
            helpWindow.show();
            assertTrue(helpWindow.isShowing());
        });

        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            Scene scene = helpWindow.getRoot().getScene();
            scene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(
                    javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        });

        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> assertFalse(helpWindow.isShowing()));
    }
}
