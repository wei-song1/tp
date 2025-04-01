package seedu.finclient.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.finclient.logic.parser.ParserUtil.parseOrder;
import static seedu.finclient.testutil.Assert.assertThrows;
import static seedu.finclient.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Company;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Job;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Networth;
import seedu.finclient.model.person.Phone;
import seedu.finclient.model.person.StockPlatform;
import seedu.finclient.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhoneList_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhoneList((Collection<String>) null));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // ============================
    // parseOrder Tests
    // ============================

    @Test
    public void parseOrder_nullOrder_throwsNullPointerException() {
        // The "order" argument itself is null => requireNonNull(order) triggers NPE.
        assertThrows(NullPointerException.class, () ->
                parseOrder(null, "10", "5.00"));
    }

    @Test
    public void parseOrder_invalidOrderType_throwsParseException() {
        // orderType must be BUY or SELL. "xyz" => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("xyz", "10", "5.00"));
    }

    @Test
    public void parseOrder_nonIntegerQuantity_throwsParseException() {
        // "10.5" is not a valid integer => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("BUY", "10.5", "5.00"));
    }

    @Test
    public void parseOrder_zeroQuantity_throwsParseException() {
        // quantity must be positive => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("SELL", "0", "5.00"));
    }

    @Test
    public void parseOrder_negativeQuantity_throwsParseException() {
        // quantity must be > 0 => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("BUY", "-10", "5.00"));
    }

    @Test
    public void parseOrder_invalidPrice_throwsParseException() {
        // "abc" is not a valid price => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("BUY", "10", "abc"));
    }

    @Test
    public void parseOrder_negativePrice_throwsParseException() {
        // Price must be > 0 => parseException
        assertThrows(ParseException.class, () ->
                parseOrder("BUY", "10", "-1.23"));
    }

    @Test
    public void parseOrder_zeroPrice_throwsParseException() {
        // Price must be strictly > 0
        assertThrows(ParseException.class, () ->
                parseOrder("SELL", "10", "0"));
    }

    @Test
    public void parseOrder_validInputs_noException() throws Exception {
        // Trimmed or untrimmed "buy" is valid
        // "10" => int, "5.00" => valid price
        Order order = parseOrder("  buy  ", "  10 ", "  5.00  ");

        // Verify the parsed Order’s fields
        assertEquals(Order.OrderType.BUY, order.getOrderType());
        assertEquals(10, order.getQuantity());
        assertEquals(5.00, order.getPrice(), 1e-9);
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany((String) null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(""));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsName() throws Exception {
        Company expectedCompany = new Company(VALID_NAME);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_NAME));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Company expectedCompany = new Company(VALID_NAME);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseJob_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseJob((String) null));
    }

    @Test
    public void parseJob_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(""));
    }

    @Test
    public void parseJob_validValueWithoutWhitespace_returnsName() throws Exception {
        Job expectedJob = new Job(VALID_NAME);
        assertEquals(expectedJob, ParserUtil.parseJob(VALID_NAME));
    }

    @Test
    public void parseJob_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String jobWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Job expectedJob = new Job(VALID_NAME);
        assertEquals(expectedJob, ParserUtil.parseJob(jobWithWhitespace));
    }

    @Test
    public void parseStockPlatform_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStockPlatform((String) null));
    }

    @Test
    public void parseStockPlatform_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(""));
    }

    @Test
    public void parseStockPlatform_validValueWithoutWhitespace_returnsName() throws Exception {
        StockPlatform expectedStockPlatform = new StockPlatform(VALID_NAME);
        assertEquals(expectedStockPlatform, ParserUtil.parseStockPlatform(VALID_NAME));
    }

    @Test
    public void parseStockPlatform_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String stockPlatformWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        StockPlatform expectedStockPlatform = new StockPlatform(VALID_NAME);
        assertEquals(expectedStockPlatform, ParserUtil.parseStockPlatform(stockPlatformWithWhitespace));
    }

    @Test
    public void parseNetworth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNetworth((String) null));
    }

    @Test
    public void parseNetworth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth("0.5"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth("-0.5"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth("2147483648"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNetworth("999999999999"));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNetworth(null));
    }

    @Test
    public void parseNetworth_validValueWithoutWhitespace_returnsName() throws Exception {
        Networth expectedNetworth = new Networth("0");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("0"));

        expectedNetworth = new Networth("150000");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("150000"));

        expectedNetworth = new Networth("9999999");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("9999999"));

        // Considered in the same net worth bracket
        expectedNetworth = new Networth("10000");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("10000"));

        expectedNetworth = new Networth("1");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("< $100k"));

        expectedNetworth = new Networth("< $100k");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth("< $100k"));
    }

    @Test
    public void parseNetworth_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String networthWithWhitespace = WHITESPACE + "0" + WHITESPACE;
        Networth expectedNetworth = new Networth("0");
        assertEquals(expectedNetworth, ParserUtil.parseNetworth(networthWithWhitespace));
    }
}
