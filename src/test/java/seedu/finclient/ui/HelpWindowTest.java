package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.input.KeyCode;

public class HelpWindowTest extends UiPartExtension {

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

    @Test
    public void handleHelp() {
        robot.interact(helpWindow::show);
        robot.press(KeyCode.valueOf("ESCAPE"));
        robot.release(KeyCode.valueOf("ESCAPE"));
        assertFalse(helpWindow.isShowing());
    }

}
