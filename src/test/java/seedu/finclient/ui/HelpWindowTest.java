package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.input.KeyCode;

/**
 * Tests HelpWindow function.
 * Runs and passes in Windows and MacOS environment.
 * Reuses code from AB4.
 */


@DisabledIfSystemProperty(named = "os.name", matches = "(?i).*(linux|ubuntu).*")
public class HelpWindowTest extends StageExtension {

    //@@author TanJieHaoAmos - reused
    //{Parts of the code below is obtained from AB4, a SE-EDU project}

    @RegisterExtension
    public final UiPartExtension uiPartExtension = new UiPartExtension();
    private HelpWindow helpWindow;
    private FxRobot robot = new FxRobot();


    @BeforeEach
    public void setUp() throws Exception {
        robot.interact(() -> helpWindow = new HelpWindow());
        FxToolkit.registerStage(helpWindow::getRoot);
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        robot.interact(helpWindow::show);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        assertFalse(helpWindow.isShowing());
    }
    //@@author

    @Test
    public void getHelpLabel_notNull() {
        assertNotNull(helpWindow.getHelpLabel());
    }

    @Test
    public void handleHelp() {
        robot.interact(helpWindow::show);
        robot.press(KeyCode.valueOf("ESCAPE"));
        robot.release(KeyCode.valueOf("ESCAPE"));
        assertFalse(helpWindow.isShowing());
    }
}
