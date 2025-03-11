package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Test class for HelpWindow.
 */
@ExtendWith(ApplicationExtension.class)
public class HelpWindowTest {

    private HelpWindow helpWindow;

    @Start
    private void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
        helpWindow.show();
    }

    @Test
    void testEscapeKeyClosesWindow(FxRobot robot) throws InterruptedException {
        // Assert that the HelpWindow is visible initially
        assertTrue(helpWindow.isShowing());

        // Simulate pressing ESC key
        robot.press(KeyCode.ESCAPE).release(KeyCode.ESCAPE);
        sleep(500);
        // Assert that the window is now closed (invisible)
        assertFalse(helpWindow.isShowing());
    }
}
