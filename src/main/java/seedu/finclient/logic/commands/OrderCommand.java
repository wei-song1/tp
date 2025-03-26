package seedu.finclient.logic.commands;

import static seedu.finclient.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.finclient.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.Messages;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Person;

/**
 * Creates a limit order for a given contact.
 */
public class OrderCommand extends Command {

    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a limit order for a given contact.\n"
            + "Parameters: "
            + "INDEX "
            + PREFIX_ORDER + "ORDER_TYPE "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_PRICE + "PRICE\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_ORDER + "BUY "
            + PREFIX_AMOUNT + "10 "
            + PREFIX_PRICE + "5.50\n";

    public static final String MESSAGE_SUCCESS = "Order generated successfully!";

    private final Index targetIndex;
    private final Order order;

    /**
     * Creates an OrderCommand to create a limit order for the specified {@code Person}.
     */
    public OrderCommand(Index index, Order order) {
        this.targetIndex = index;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        personToEdit.setUnhidden();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhoneList(), personToEdit.getEmail(),
                personToEdit.getAddress(), order, personToEdit.getRemark(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderCommand // instanceof handles nulls
                && targetIndex.equals(((OrderCommand) other).targetIndex)
                && order.equals(((OrderCommand) other).order));
    }
}
