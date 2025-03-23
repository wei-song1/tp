package seedu.finclient.ui;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Provides an isolated stage to test an individual {@code UiPart}.
 * Reuses code from AB4.
 */
public class UiPartExtension extends StageExtension {

    //@@author TanJieHaoAmos - reused
    //{Parts of the code below is obtained from AB4, a SE-EDU project}
    private static final String[] CSS_FILES = {"view/DarkTheme.css", "view/Extensions.css"};

    public void setUiPart(final UiPart<? extends Parent> uiPart) {
        try {
            FxToolkit.setupScene(() -> {
                Scene scene = new Scene(uiPart.getRoot());
                scene.getStylesheets().setAll(CSS_FILES);
                return scene;
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Timeout should not happen.", te);
        }
    }
    //@@author
}

