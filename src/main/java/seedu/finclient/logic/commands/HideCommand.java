package seedu.finclient.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.finclient.model.Model;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

/**
 * Hides details of the specific person in the address book to the user.
 */
public class HideCommand extends Command {
    public static final String COMMAND_WORD = "hide";

    public static final String MESSAGE_SUCCESS = "Hid the person's details";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Hides all persons' details whose names contain "
            + "any of the specified keywords (case-insensitive) and displays 'Hidden' instead.\n"
            + "Parameters: all or KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public HideCommand() {
        this.predicate = null;
    }

    public HideCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (predicate == null) {
            model.hideAllPersons();
        } else {
            model.hidePerson(predicate);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HideCommand)) {
            return false;
        }

        HideCommand otherHideCommand = (HideCommand) other;
        return predicate.equals(otherHideCommand.predicate);
    }

    @Override
    public String toString() {
        return predicate == null ? "Hide all persons" : "Hide persons with " + predicate.toString();
    }
}
