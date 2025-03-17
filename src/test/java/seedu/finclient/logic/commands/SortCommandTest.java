package seedu.finclient.logic.commands;

import static seedu.finclient.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finclient.logic.commands.SortCommand.MESSAGE_SORT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.Model;
import seedu.finclient.model.ModelManager;

public class SortCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    @Test
    public void execute_sort_success() {
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "name"), false, false);
        assertCommandSuccess(new SortCommand("name"), model, expectedCommandResult, expectedModel);
    }
}
