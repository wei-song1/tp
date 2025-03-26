package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.AMOUNT_DESC_10;
import static seedu.finclient.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_DUPLICATE_PHONE_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_EXCEED_PHONE_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_QUANTITY_ZERO;
import static seedu.finclient.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.finclient.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.JOB_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.NETWORTH_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.NETWORTH_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.ORDER_DESC_BUY;
import static seedu.finclient.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.finclient.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.finclient.logic.commands.CommandTestUtil.PRICE_DESC_550;
import static seedu.finclient.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.STOCK_PLATFORM_DESC_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.STOCK_PLATFORM_DESC_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.finclient.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_NETWORTH_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_NETWORTH_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_STOCK_PLATFORM_AMY;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_STOCK_PLATFORM_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finclient.testutil.TypicalPersons.AMY;
import static seedu.finclient.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.finclient.logic.Messages;
import seedu.finclient.logic.commands.AddCommand;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Person;
import seedu.finclient.model.person.Phone;
import seedu.finclient.model.person.PhoneList;
import seedu.finclient.model.tag.Tag;
import seedu.finclient.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).withRemark(VALID_REMARK_BOB)
                .withCompany(VALID_COMPANY_BOB).withJob(VALID_JOB_BOB).withStockPlatform(VALID_STOCK_PLATFORM_BOB)
                .withNetworth(VALID_NETWORTH_BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND + COMPANY_DESC_BOB + JOB_DESC_BOB
                + STOCK_PLATFORM_DESC_BOB + NETWORTH_DESC_BOB, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).withCompany(VALID_COMPANY_BOB).withJob(VALID_JOB_BOB)
                .withStockPlatform(VALID_STOCK_PLATFORM_BOB).withNetworth(VALID_NETWORTH_BOB).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + COMPANY_DESC_BOB + JOB_DESC_BOB
                        + STOCK_PLATFORM_DESC_BOB + NETWORTH_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + REMARK_DESC_AMY + COMPANY_DESC_AMY + JOB_DESC_AMY + STOCK_PLATFORM_DESC_AMY
                        + NETWORTH_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REMARK_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + REMARK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePhoneNumbers_failure() {
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_DUPLICATE_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                PhoneList.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_exceedPhoneNumbers_failure() {
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_EXCEED_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                PhoneList.SIZE_CONSTRAINTS);
    }

    // ======================
    // Order-related tests
    // ======================

    @Test
    public void parse_orderFieldsPresent_success() {
        // Scenario: user wants to add a new person with a BUY order, quantity=10, price=5.50
        // This is combined with the rest of the required fields (name, phone, email, address).
        Person expectedPerson = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND)
                .withRemark(VALID_REMARK_BOB)
                .withOrder("BUY 10 @ $5.50")
                .withCompany(VALID_COMPANY_BOB)
                .withJob(VALID_JOB_BOB)
                .withStockPlatform(VALID_STOCK_PLATFORM_BOB)
                .withNetworth(VALID_NETWORTH_BOB)
                .build();

        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND + COMPANY_DESC_BOB + JOB_DESC_BOB + STOCK_PLATFORM_DESC_BOB + NETWORTH_DESC_BOB
                + ORDER_DESC_BUY + AMOUNT_DESC_10 + PRICE_DESC_550;

        assertParseSuccess(parser, userInput, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_noOrderFieldsProvided_orderDefaultsToNone() {
        // If user does NOT specify any order/amount/price, the parser sets order to NONE by default.
        Person expectedPerson = new PersonBuilder(AMY)
                .withRemark(VALID_REMARK_AMY)
                .withOrder("NONE")
                .withTags()
                .withCompany(VALID_COMPANY_AMY)
                .withJob(VALID_JOB_AMY)
                .withStockPlatform(VALID_STOCK_PLATFORM_AMY)
                .withNetworth(VALID_NETWORTH_AMY)
                .build();

        String userInput = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY
                + COMPANY_DESC_AMY + JOB_DESC_AMY + STOCK_PLATFORM_DESC_AMY + NETWORTH_DESC_AMY;
        assertParseSuccess(parser, userInput, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_orderSuppliedButMissingAmount_failure() {
        // User specified an order type but did NOT supply the --amount prefix => leads to parse error.
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESC_BUY // no amount
                + REMARK_DESC_BOB
                + PRICE_DESC_550;

        assertParseFailure(parser, userInput, Order.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_orderSuppliedButMissingPrice_failure() {
        // User specified an order type + amount but NOT the price => parse error
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESC_BUY + REMARK_DESC_BOB + AMOUNT_DESC_10; // no price

        assertParseFailure(parser, userInput, Order.MESSAGE_CONSTRAINTS
        );
    }

    @Test
    public void parse_invalidOrderType_failure() {
        // "foo" is not "buy" or "sell"
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_ORDER_DESC + AMOUNT_DESC_10 + REMARK_DESC_BOB + PRICE_DESC_550;

        assertParseFailure(parser, userInput, "Invalid order type! Must be either BUY or SELL");
    }

    @Test
    public void parse_invalidQuantity_failure() {
        // Non-integer or zero/negative => parse failure
        // Example with "abc" as quantity
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESC_BUY + INVALID_AMOUNT_DESC + REMARK_DESC_BOB + PRICE_DESC_550; // "abc"
        assertParseFailure(parser, userInput, "Quantity must be a valid integer.");

        // Example with "0" quantity => parse fails with "Quantity should be a positive integer"
        String userInputZeroQty = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESC_BUY + INVALID_QUANTITY_ZERO + REMARK_DESC_BOB + PRICE_DESC_550; // "0"
        assertParseFailure(parser, userInputZeroQty, Order.MESSAGE_CONSTRAINTS_QUANTITY);
    }

    @Test
    public void parse_invalidPrice_failure() {
        // Negative or invalid format => parse failure
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORDER_DESC_BUY + AMOUNT_DESC_10 + REMARK_DESC_BOB + INVALID_PRICE_DESC; // "-1.50"
        assertParseFailure(parser, userInput, Order.MESSAGE_CONSTRAINTS_PRICE);
    }
}
