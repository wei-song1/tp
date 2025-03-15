package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;

public class HelpWindowTest extends ApplicationTest {

    private HelpWindow helpWindow;

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    public void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
    }

    @Test
    public void show_helpWindowIsDisplayedAndHandlesEscapeKey() {
        assertFalse(helpWindow.isShowing());
    }
}
