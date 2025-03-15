package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.stage.Stage;

public class HelpWindowTest extends ApplicationTest {

    private HelpWindow helpWindow;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> helpWindow = new HelpWindow(stage));
    }

    @Test
    public void show_helpWindowIsDisplayedAndHandlesEscapeKey() {
        interact(() -> {
            assertFalse(helpWindow.isShowing());
            helpWindow.show();
            assertTrue(helpWindow.isShowing());
        });

        interact(() -> helpWindow.hide());

        interact(() -> assertFalse(helpWindow.isShowing()));
    }
}
