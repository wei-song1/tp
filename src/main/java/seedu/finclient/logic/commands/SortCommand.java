package seedu.finclient.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.finclient.commons.util.ToStringBuilder;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;

/**
 * Sorts the list of contacts by some criteria.
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort all contacts by given criteria.\n"
            + "Parameters: criteria (name, networth, price, amount, deadline)\n"
            + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SORT_ACKNOWLEDGEMENT = "Sorted contacts by criteria: %1$s";
    private static final List<String> validCriteria = Arrays.asList("name", "price", "networth", "amount", "deadline");
    private final String criteria;

    /**
     * Creates a SortCommand to sort the list of contacts by the given criteria.
     */
    public SortCommand(String criteria) {
        requireNonNull(criteria);
        this.criteria = criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortPersons(criteria);
        return new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, criteria));
    }

    /**
     * Returns true if the given criteria is a valid criteria.
     */
    public static boolean isValidCriteria(String criteria) {
        requireNonNull(criteria);
        return validCriteria.contains(criteria);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && criteria.equals(((SortCommand) other).criteria)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("criteria", criteria)
                .toString();
    }
}
