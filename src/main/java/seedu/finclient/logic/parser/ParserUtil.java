package seedu.finclient.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.model.person.Remark.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.commons.util.StringUtil;
import seedu.finclient.logic.commands.remark.CommandType;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Company;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Job;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Networth;
import seedu.finclient.model.person.Phone;
import seedu.finclient.model.person.PhoneList;
import seedu.finclient.model.person.Remark;
import seedu.finclient.model.person.StockPlatform;
import seedu.finclient.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }



    /**
     * Parses a collection of phone numbers into a list of Phone objects for PhoneList.
     *
     * @throws ParseException if any phone number is duplicated or contains more than 3 numbers.
     */
    public static PhoneList parsePhoneList(Collection<String> phones) throws ParseException {
        requireNonNull(phones);

        Set<String> uniquePhones = new HashSet<>(phones);
        if (uniquePhones.size() < phones.size()) {
            throw new ParseException(PhoneList.MESSAGE_CONSTRAINTS);
        }

        if (uniquePhones.size() > 3) {
            throw new ParseException(PhoneList.SIZE_CONSTRAINTS);
        }

        ArrayList<Phone> phoneArrayList = new ArrayList<>();
        for (String phone : phones) {
            phoneArrayList.add(parsePhone(phone));
        }

        return new PhoneList(phoneArrayList);
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a collection of {@code String order}, {@code String amount}
     * and {@code String price} into an {@code Order}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given arguments are invalid (e.g. wrong order type,
     *                        non-integer quantity, or an invalid price format).
     */
    public static Order parseOrder(String order, String amount, String price) throws ParseException {
        requireNonNull(order);

        // Trim whitespace
        String trimmedOrder = order.trim();
        String trimmedAmount = amount.trim();
        String trimmedPrice = price.trim();

        // 1) Parse the order type (BUY or SELL)
        Order.OrderType orderType;
        try {
            // Convert to uppercase to match enum constants
            orderType = Order.OrderType.valueOf(trimmedOrder.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid order type! Must be either BUY or SELL");
        }

        // 2) Parse quantity as int
        long quantity;
        try {
            quantity = Long.parseLong(trimmedAmount);
        } catch (NumberFormatException e) {
            throw new ParseException("Quantity must be a valid integer.");
        }

        if (!Order.isValidPrice(trimmedPrice)) {
            throw new ParseException(Order.MESSAGE_CONSTRAINTS_PRICE);
        }

        if (!Order.isValidQuantity(quantity)) {
            throw new ParseException(Order.MESSAGE_CONSTRAINTS_QUANTITY);
        }

        return new Order(orderType, trimmedPrice, quantity);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Remark} is invalid.
     */
    public static Remark parseRemark(String input, CommandType type) throws ParseException {
        if (input.isEmpty() && type.equals(CommandType.EDIT)) {
            return new Remark("", Optional.empty());
        }
        requireNonNull(input);
        String[] parts = input.split("by/");
        String text = parts[0].trim();
        if (!Remark.isValidRemark(text)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        Optional<LocalDateTime> ts = Optional.empty();
        if (parts.length > 1) {
            String dateTimeStr = parts[1].trim();
            String regexPattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"; // Expected format: yyyy-MM-dd HH:mm
            // First, validate the structure of the timestamp using regex.
            if (!dateTimeStr.matches(regexPattern)) {
                throw new ParseException("Timestamp format is invalid. Expected format: yyyy-MM-dd HH:mm");
            }

            // Next, parse the string into LocalDateTime and catch any semantic errors (e.g. invalid dates).
            try {
                ts = Optional.of(LocalDateTime.parse(dateTimeStr, FORMATTER));
            } catch (DateTimeParseException e) {
                throw new ParseException("Timestamp value is invalid. Please enter a real date and time.");
            }
        }
        return new Remark(text, ts);
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code String job} into an {@code Job}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code job} is invalid.
     */
    public static Job parseJob(String job) throws ParseException {
        requireNonNull(job);
        String trimmedJob = job.trim();
        if (!Job.isValidJob(trimmedJob)) {
            throw new ParseException(Job.MESSAGE_CONSTRAINTS);
        }
        return new Job(trimmedJob);
    }

    /**
     * Parses a {@code String platform} into an {@code StockPlatform}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code platform} is invalid.
     */
    public static StockPlatform parseStockPlatform(String platform) throws ParseException {
        requireNonNull(platform);
        String trimmedPlatform = platform.trim();
        if (!StockPlatform.isValidStockPlatform(trimmedPlatform)) {
            throw new ParseException(StockPlatform.MESSAGE_CONSTRAINTS);
        }
        return new StockPlatform(trimmedPlatform);
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Networth parseNetworth(String networth) throws ParseException {
        requireNonNull(networth);
        String trimmedNetworth = networth.trim();
        if (!Networth.isValidNetworth(trimmedNetworth)) {
            throw new ParseException(Networth.MESSAGE_CONSTRAINTS);
        }

        if (!Networth.isBracket(trimmedNetworth)) {
            if (!Networth.isValidAmount(trimmedNetworth)) {
                throw new ParseException(Networth.MESSAGE_CONSTRAINTS);
            }
        }

        return new Networth(trimmedNetworth);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
