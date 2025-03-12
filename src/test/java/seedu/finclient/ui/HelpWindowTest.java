package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Test class for HelpWindow.
 */
@ExtendWith(ApplicationExtension.class)
public class HelpWindowTest {

    private HelpWindow helpWindow;

    @BeforeAll
    static void setup() {
        System.setProperty("javafx.platform", "Headless");
    }
    
    @Start
    private void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
        helpWindow.show();
    }

    @Test
    void testEscapeKeyClosesWindow(FxRobot robot) {
        // Assert that the help window is opened
        assertTrue(helpWindow.isShowing());

        // Simulate pressing ESC key
        robot.press(KeyCode.ESCAPE).release(KeyCode.ESCAPE);

        // Wait for JavaFX events to process
        WaitForAsyncUtils.waitForFxEvents();

        // Assert that the window is closed
        assertFalse(helpWindow.isShowing());
    }
}