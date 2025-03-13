package seedu.finclient.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.finclient.model.Model;
import seedu.finclient.model.ModelManager;
import seedu.finclient.model.UserPrefs;

import static seedu.finclient.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finclient.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.finclient.logic.commands.SortCommand.MESSAGE_SORT_ACKNOWLEDGEMENT;
import static seedu.finclient.testutil.TypicalPersons.getTypicalFinClient;

public class SortCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    @Test
    public void execute_sort_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_SORT_ACKNOWLEDGEMENT, false, false);
        assertCommandSuccess(new SortCommand(), model, expectedCommandResult, expectedModel);
    }
}
