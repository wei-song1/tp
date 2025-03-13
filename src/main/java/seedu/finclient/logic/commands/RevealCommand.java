package seedu.finclient.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.finclient.model.Model;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

/**
 * Reveals details of the specific person in the address book to the user.
 */
public class RevealCommand extends Command {
    public static final String COMMAND_WORD = "reveal";

    public static final String MESSAGE_SUCCESS = "Revealed the person's details";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reveals persons' perhaps hidden details "
            + "whose names contain any of the specified keywords (case-insensitive)\n"
            + "Parameters: all or KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public RevealCommand() {
        this.predicate = null;
    }

    public RevealCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (predicate == null) {
            model.revealAllPersons();
        } else {
            model.revealPerson(predicate);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RevealCommand)) {
            return false;
        }

        RevealCommand otherRevealCommand = (RevealCommand) other;
        return predicate.equals(otherRevealCommand.predicate);
    }

    @Override
    public String toString() {
        return predicate == null ? "Reveal all persons" : "Reveal persons with " + predicate.toString();
    }
}
