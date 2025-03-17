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
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_SORT_ACKNOWLEDGEMENT, "name"), false, false);
        assertCommandSuccess(new SortCommand("name"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void isValidCriteria() {
        // null criteria
        assertThrows(NullPointerException.class, () -> SortCommand.isValidCriteria(null));

        // invalid criteria
        assertFalse(SortCommand.isValidCriteria("invalid"));

        // valid criteria
        assertTrue(SortCommand.isValidCriteria("name"));
        assertTrue(SortCommand.isValidCriteria("phone"));
    }

    @Test
    public void equals() {
        SortCommand sortNameCommand = new SortCommand("name");
        SortCommand sortPhoneCommand = new SortCommand("phone");

        // same object -> returns true
        assertTrue(sortNameCommand.equals(sortNameCommand));

        // same values -> returns true
        SortCommand sortNameCommandCopy = new SortCommand("name");
        assertTrue(sortNameCommand.equals(sortNameCommandCopy));

        // different types -> returns false
        assertFalse(sortNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortNameCommand.equals(null));

        // different criteria -> returns false
        assertFalse(sortNameCommand.equals(sortPhoneCommand));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortNameCommand = new SortCommand("name");
        String expected = SortCommand.class.getCanonicalName() + "{criteria=" + "name" + "}";
        assertEquals(expected, sortNameCommand.toString());
    }
}
