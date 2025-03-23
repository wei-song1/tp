package seedu.finclient.ui;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testfx.api.FxToolkit;

/**
 * Properly sets up and tears down a JavaFx stage for our testing purposes.
 */
public class StageExtension implements BeforeEachCallback, AfterEachCallback {

    //@@author TanJieHaoAmos - reused
    //{Parts of the code below is obtained from AB4, a SE-EDU project}
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        FxToolkit.cleanupStages();
    }

    //@@author
}

