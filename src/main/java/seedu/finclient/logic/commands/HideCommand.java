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
 * Hides details of the specific person in the address book to the user.
 */
public class HideCommand extends Command {
    public static final String COMMAND_WORD = "hide";

    public static final String MESSAGE_SUCCESS = "Hid details for the specified person(s).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Hides details of person(s).\n"
            + "Parameters: all | INDEX | KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " all\n"
            + "  " + COMMAND_WORD + " 1\n"
            + "  " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    private final Index targetIndex;

    /**
     * Creates a HideCommand to hide all persons.
     */
    public HideCommand() {
        this.targetIndex = null;
        this.predicate = null;
    }

    /**
     * Creates a HideCommand to hide the person at the specified {@code Index}.
     */
    public HideCommand(Index index) {
        this.targetIndex = index;
        this.predicate = null;
    }

    /**
     * Creates a HideCommand to hide persons with the specified {@code NameContainsKeywordsPredicate}.
     */
    public HideCommand(NameContainsKeywordsPredicate predicate) {
        this.targetIndex = null;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1) No predicate & no index => hide all
        // 2) Has index (and no predicate) => hide by index
        // 3) Otherwise, we have a predicate => hide by matching names
        if (predicate == null && targetIndex == null) {
            model.hideAllPersons();
        } else if (predicate == null) {
            // targetIndex != null
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToHide = lastShownList.get(targetIndex.getZeroBased());
            model.hidePerson(personToHide);
        } else {

            if (!model.hasPerson(predicate)) {
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
            }

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
        return (predicate == null && otherHideCommand.predicate == null
                || predicate != null && predicate.equals(otherHideCommand.predicate))
                && (targetIndex == null && otherHideCommand.targetIndex == null
                || targetIndex != null && targetIndex.equals(otherHideCommand.targetIndex));
    }

    @Override
    public String toString() {
        return predicate == null ? "Hide all persons" : "Hide persons with " + predicate.toString();
    }
}
