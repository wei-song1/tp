package seedu.finclient.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;

/**
 * Sorts the list of contacts by some criteria.
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort all contacts by given criteria.\n"
            + "Parameters: criteria (name/tag)\n"
            + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SORT_ACKNOWLEDGEMENT = "Sorted contacts by criteria: %1$s";
    private static final List<String> validCriteria = Arrays.asList("name", "tag");
    private final String criteria;

    public SortCommand(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortPersons(criteria);
        return new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, criteria));
    }

    public static boolean isValidCriteria(String criteria) {
        return validCriteria.contains(criteria);
    }
}
