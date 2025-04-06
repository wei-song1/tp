package seedu.finclient.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.Messages;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;
import seedu.finclient.model.person.Person;

/**
 * Reveals details of the specific person in the address book to the user.
 */
public class RevealCommand extends Command {
    public static final String COMMAND_WORD = "reveal";

    public static final String MESSAGE_SUCCESS = "Revealed details for the specified person(s).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reveals hidden details of person(s).\n"
            + "Parameters: all | INDEX | KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " all\n"
            + "  " + COMMAND_WORD + " 1\n"
            + "  " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    private final Index targetIndex;

    /**
     * Creates a RevealCommand to reveal all persons.
     */
    public RevealCommand() {
        this.targetIndex = null;
        this.predicate = null;
    }

    /**
     * Creates a RevealCommand to reveal the person at the specified {@code Index}.
     */
    public RevealCommand(Index index) {
        this.targetIndex = index;
        this.predicate = null;
    }

    /**
     * Creates a RevealCommand to reveal persons with the specified {@code NameContainsKeywordsPredicate}.
     */
    public RevealCommand(NameContainsKeywordsPredicate predicate) {
        this.targetIndex = null;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1) No predicate & no index => reveal all
        // 2) Has index (and no predicate) => reveal by index
        // 3) Otherwise, we have a predicate => reveal by predicate
        if (predicate == null && targetIndex == null) {
            model.revealAllPersons();
        } else if (predicate == null) {
            // targetIndex != null
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToReveal = lastShownList.get(targetIndex.getZeroBased());
            model.revealPerson(personToReveal);
        } else {

            if (!model.hasPerson(predicate)) {
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
            }

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
        return (predicate == null && otherRevealCommand.predicate == null
                || predicate != null && predicate.equals(otherRevealCommand.predicate))
                && (targetIndex == null && otherRevealCommand.targetIndex == null
                || targetIndex != null && targetIndex.equals(otherRevealCommand.targetIndex));
    }

    @Override
    public String toString() {
        return predicate == null ? "Reveal all persons" : "Reveal persons with " + predicate.toString();
    }
}
