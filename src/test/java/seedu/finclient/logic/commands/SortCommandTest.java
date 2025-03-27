package seedu.finclient.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finclient.logic.commands.SortCommand.MESSAGE_SORT_ACKNOWLEDGEMENT;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.Model;
import seedu.finclient.model.ModelManager;

public class SortCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void constructor_nullCriteria_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    public void execute_sort_success() {
        CommandResult expectedCommandResultName =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "name"), false, false);
        CommandResult expectedCommandResultAmount =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "amount"), false, false);
        CommandResult expectedCommandResultNetworth =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "networth"), false, false);
        CommandResult expectedCommandResultPrive =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "price"), false, false);
        assertCommandSuccess(new SortCommand("name"), model, expectedCommandResultName, expectedModel);
        assertCommandSuccess(new SortCommand("amount"), model, expectedCommandResultAmount, expectedModel);
        assertCommandSuccess(new SortCommand("networth"), model, expectedCommandResultNetworth, expectedModel);
        assertCommandSuccess(new SortCommand("price"), model, expectedCommandResultPrive, expectedModel);
    }

    @Test
    public void isValidCriteria() {
        // null criteria
        assertThrows(NullPointerException.class, () -> SortCommand.isValidCriteria(null));

        // invalid criteria
        assertFalse(SortCommand.isValidCriteria("invalid"));
        assertFalse(SortCommand.isValidCriteria("name price"));
        assertFalse(SortCommand.isValidCriteria("name price amount"));
        assertFalse(SortCommand.isValidCriteria(" "));

        // valid criteria
        assertTrue(SortCommand.isValidCriteria("name"));
        assertTrue(SortCommand.isValidCriteria("amount"));
        assertTrue(SortCommand.isValidCriteria("networth"));
        assertTrue(SortCommand.isValidCriteria("price"));
    }

    @Test
    public void equals() {
        SortCommand sortNameCommand = new SortCommand("name");
        SortCommand sortAmountCommand = new SortCommand("amount");

        // same object -> returns true
        assertTrue(sortNameCommand.equals(sortNameCommand));

        // same values -> returns true
        SortCommand sortNameCommandCopy = new SortCommand("name");
        assertTrue(sortNameCommand.equals(sortNameCommandCopy));

        // different types -> returns false
        assertFalse(sortNameCommand.equals(1));
        assertFalse(sortNameCommand.equals("name"));

        // null -> returns false
        assertFalse(sortNameCommand.equals(null));

        // different criteria -> returns false
        assertFalse(sortNameCommand.equals(sortAmountCommand));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortNameCommand = new SortCommand("name");
        String expected = SortCommand.class.getCanonicalName() + "{criteria=" + "name" + "}";
        assertEquals(expected, sortNameCommand.toString());
    }
}
