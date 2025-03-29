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

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
    public void testHelpLabelText() {
        String labelText = helpWindow.getHelpLabel().getText();
        assertFalse(labelText.equals(""));
        assertTrue(labelText.startsWith("Welcome to FinClient")
                && labelText.endsWith("https://ay2425s2-cs2103t-t11-4.github.io/tp/UserGuide.html"));
    }

    @Test
    public void handleHelp() {
        robot.interact(helpWindow::show);
        robot.clickOn(helpWindow.getRoot().getScene().getRoot());
        robot.press(KeyCode.valueOf("ESCAPE"));
        robot.release(KeyCode.valueOf("ESCAPE"));
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void testGetCopyButton() {
        Button copyButton = helpWindow.getCopyButton();
        assertTrue(copyButton != null, "Copy Button should not be null");
    }

    @Test
    public void testGetHelpScrollPane() {
        ScrollPane scrollPane = helpWindow.getHelpScrollPane();
        assertTrue(scrollPane != null, "Scroll Pane should not be null");
    }

    @Test
    public void testScrollPaneButtonVisibility() {
        robot.interact(helpWindow::show);
        ScrollPane scrollPane = helpWindow.getHelpScrollPane();
        Button copyButton = helpWindow.getCopyButton();
        assertFalse(copyButton.isVisible(),
                "End button should not be visible at the start of scroll");
        scrollPane.setVvalue(1.0);
        assertTrue(copyButton.isVisible(),
                "End button should be visible when scrolled to the bottom");
    }
}
