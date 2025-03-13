package seedu.finclient.logic.commands;

import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;

/**
 * Sorts the list of contacts by some criteria.
 */

public class SortCommand extends Command {
    public static final String MESSAGE_SORT_ACKNOWLEDGEMENT = "Sorted contacts by some criteria";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Sorted contacts by some criteria");
    };
}
