package seedu.finclient.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_NETWORTH;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_PLATFORM;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.finclient.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.FinClient;
import seedu.finclient.model.Model;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;
import seedu.finclient.model.person.Person;
import seedu.finclient.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final List<String> VALID_PHONE_LIST_AMY = Arrays.asList("11111111");
    public static final List<String> VALID_PHONE_LIST_BOB = Arrays.asList("22222222");
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_COMPANY_AMY = "NUS";
    public static final String VALID_COMPANY_BOB = "NTU";
    public static final String VALID_JOB_AMY = "Student";
    public static final String VALID_JOB_BOB = "Researcher";
    public static final String VALID_STOCK_PLATFORM_AMY = "eduRec";
    public static final String VALID_STOCK_PLATFORM_BOB = "uNivUS";
    public static final String VALID_NETWORTH_AMY = "< $100k";
    public static final String VALID_NETWORTH_BOB = "< $100k";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String JOB_DESC_AMY = " " + PREFIX_JOB + VALID_JOB_AMY;
    public static final String JOB_DESC_BOB = " " + PREFIX_JOB + VALID_JOB_BOB;
    public static final String STOCK_PLATFORM_DESC_AMY = " " + PREFIX_PLATFORM + VALID_STOCK_PLATFORM_AMY;
    public static final String STOCK_PLATFORM_DESC_BOB = " " + PREFIX_PLATFORM + VALID_STOCK_PLATFORM_BOB;
    public static final String NETWORTH_DESC_AMY = " " + PREFIX_NETWORTH + VALID_NETWORTH_AMY;
    public static final String NETWORTH_DESC_BOB = " " + PREFIX_NETWORTH + VALID_NETWORTH_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DUPLICATE_PHONE_DESC = " " + PREFIX_PHONE + "911" + " " + PREFIX_PHONE + "911";
    public static final String INVALID_EXCEED_PHONE_DESC = " " + PREFIX_PHONE + "111" + " " + PREFIX_PHONE + "222"
            + " " + PREFIX_PHONE + "333" + " " + PREFIX_PHONE + "444";
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "delete";
    public static final String INVALID_JOB_DESC = " " + PREFIX_JOB + "delete";
    public static final String INVALID_PLATFORM_DESC = " " + PREFIX_PLATFORM + "delete";
    public static final String INVALID_NETWORTH_DESC = " " + PREFIX_NETWORTH + "delete";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_SORT_CRITERIA_NAME = "name";


    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    // For valid order inputs
    public static final String ORDER_DESC_BUY = " " + PREFIX_ORDER + "buy";
    public static final String AMOUNT_DESC_10 = " " + PREFIX_AMOUNT + "10";
    public static final String PRICE_DESC_550 = " " + PREFIX_PRICE + "5.50";

    // For invalid order fields
    public static final String INVALID_ORDER_DESC = " " + PREFIX_ORDER + "foo"; // invalid order type
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "abc"; // non-integer
    public static final String INVALID_QUANTITY_ZERO = " " + PREFIX_AMOUNT + "0"; // quantity must be > 0
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "-1.50"; // negative price

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhones(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinClient expectedFinClient = new FinClient(actualModel.getFinClient());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFinClient, actualModel.getFinClient());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
