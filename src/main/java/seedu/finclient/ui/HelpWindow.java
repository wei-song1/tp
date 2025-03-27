package seedu.finclient.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.finclient.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String ADD_MESSAGE = "Add contact (minimum of 1 and maximum of 3 phone numbers) : ";
    public static final String ADD_SYNTAX = "add n/NAME p/PHONE_NUMBER [p/PHONE_NUMBER] "
            + "[p/PHONE_NUMBER] e/EMAIL a/ADDRESS [r/REMARK] [t/TAG] "
            + "[c/COMPANY] [j/JOB] [s/PLATFORM] [$/NETWORTH]";

    public static final String EDIT_MESSAGE = "Edit contact (mininally one field is to be entered) : ";
    public static final String EDIT_SYNTAX = "edit INDEX [n/NAME] "
            + "[p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REMARK] [t/TAG] "
            + "[c/COMPANY] [j/JOB] [s/PLATFORM] [$/NETWORTH]";

    public static final String REMOVE_OPTIONAL_MESSAGE = "Edit and remove optional fields: ";
    public static final String REMOVE_OPTIONAL_SYNTAX = "edit INDEX [t/] [c/delete] "
            + "[j/delete] [s/delete] [$/delete]";

    public static final String LIST_MESSAGE = "List all saved contacts : ";
    public static final String LIST_SYNTAX = "list";

    public static final String FIND_MESSAGE = "Find contact by name : ";
    public static final String FIND_SYNTAX = "find KEYWORD [MORE_KEYWORDS]";

    public static final String DELETE_MESSAGE = "Delete contact : ";
    public static final String DELETE_SYNTAX = "delete INDEX";

    public static final String REMARK_MESSAGE = "Adding remark to contact : ";
    public static final String REMARK_SYNTAX = "remark INDEX r/[REMARK]";

    public static final String HIDE_MESSAGE = "Hide contact details (can use index or name) : ";
    public static final String HIDE_SYNTAX = "hide INDEX/NAME";

    public static final String REVEAL_MESSAGE = "Reveal contact details (can use index or name) : ";
    public static final String REVEAL_SYNTAX = "reveal INDEX/NAME";

    public static final String HIDE_REVEAL_MESSAGE = "Hide/Reveal all contacts : ";
    public static final String HIDE_REVEAL_SYNTAX = "hide all / reveal all";

    public static final String ORDER_MESSAGE = "Place order for call auctions: ";
    public static final String ORDER_SYNTAX = "order INDEX o/TYPE am/AMOUNT at/PRICE"
            + " (Available types: buy, sell)";

    public static final String REMOVE_ORDER_MESSAGE = "Remove order for call auctions: ";
    public static final String REMOVE_ORDER_SYNTAX = "order INDEX";

    public static final String CLEAR_MESSAGE = "Clear all saved contacts : ";
    public static final String CLEAR_SYNTAX = "clear";

    public static final String HELP_MESSAGE = "Getting help for available commands : ";
    public static final String HELP_SYNTAX = "help or press Fn + F1";

    public static final String EXIT_MESSAGE = "Exit FinClient : ";
    public static final String EXIT_SYNTAX = "exit or press Fn + F2";

    public static final String ESCAPE_MESSAGE = "Close help window : ";
    public static final String ESCAPE_SYNTAX = "hit the ESC key or click on the 'X'"
            + " located at the top right of this window";

    public static final String SORT_MESSAGE = "Sort contact via certain criteria : ";
    public static final String SORT_SYNTAX = "sort CRITERIA ("
            + "Available criteria: name, amount, price, networth)";

    public static final String EXTRA_INFO = "Details inside [ ] in the command examples are optional."
            + "\n\n" + "INDEX refers to the number beside the contact's name that is currently displayed.";
    public static final String URL_GUIDE_MESSAGE = "For a more detailed guide, please head to ";
    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103t-t11-4.github.io/tp/UserGuide.html";

    public static final String HELP_DISPLAY = "Welcome to FinClient, here are the commands available: \n\n"
            + ADD_MESSAGE + ADD_SYNTAX + "\n\n" + EDIT_MESSAGE + EDIT_SYNTAX + "\n\n"
            + REMOVE_OPTIONAL_MESSAGE + REMOVE_OPTIONAL_SYNTAX + "\n\n" + LIST_MESSAGE + LIST_SYNTAX + "\n\n"
            + FIND_MESSAGE + FIND_SYNTAX + "\n\n" + DELETE_MESSAGE + DELETE_SYNTAX + "\n\n"
            + REMARK_MESSAGE + REMARK_SYNTAX + "\n\n" + HIDE_MESSAGE + HIDE_SYNTAX + "\n\n"
            + REVEAL_MESSAGE + REVEAL_SYNTAX + "\n\n" + HIDE_REVEAL_MESSAGE + HIDE_REVEAL_SYNTAX + "\n\n"
            + SORT_MESSAGE + SORT_SYNTAX + "\n\n" + ORDER_MESSAGE + ORDER_SYNTAX + "\n\n"
            + REMOVE_ORDER_MESSAGE + REMOVE_ORDER_SYNTAX + "\n\n" + CLEAR_MESSAGE + CLEAR_SYNTAX + "\n\n"
            + HELP_MESSAGE + HELP_SYNTAX + "\n\n" + ESCAPE_MESSAGE + ESCAPE_SYNTAX + "\n\n"
            + EXIT_MESSAGE + EXIT_SYNTAX + "\n\n" + EXTRA_INFO + "\n\n" + URL_GUIDE_MESSAGE + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final String NOT_SHOWING = "HelpWindow is not showing but is supposed to be showing";
    private static final String SHOWING = "HelpWindow is showing but is supposed to be hidden";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        if (root == null) {
            throw new IllegalArgumentException("Root stage cannot be null.");
        }
        assert helpMessage != null : "helpMessage label is not initialized.";
        helpMessage.setText(HELP_DISPLAY);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    public Label getHelpLabel() {
        return helpMessage;
    }

    public ScrollPane getHelpScrollPane() {
        return scrollPane;
    }

    public Button getCopyButton() {
        return copyButton;
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.info("Showing help page about the application.");
        Stage stage = getRoot();

        setKeyFunction(stage, KeyCode.ESCAPE);

        setShowButton();

        getRoot().show();
        getRoot().centerOnScreen();
        assert isShowing() == true : NOT_SHOWING;
    }

    /**
     * Creates a shortcut for ESC to close HelpWindow
     */
    private void setKeyFunction(Stage stage, KeyCode key) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == key) {
                hide();
            }
        });
    }

    /**
     * Shows copyButton when scrollbar reaches the end
     */
    private void setShowButton() {
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            double max = scrollPane.getVmax();
            if (newValue.doubleValue() == max) {
                copyButton.setVisible(true);
            } else {
                copyButton.setVisible(false);
            }
        });
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        logger.info("Closing HelpWindow");
        getRoot().hide();
        assert isShowing() == false : SHOWING;
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        if (!isShowing()) {
            throw new IllegalStateException("Cannot focus HelpWindow: It is not currently visible.");
        }
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

}
