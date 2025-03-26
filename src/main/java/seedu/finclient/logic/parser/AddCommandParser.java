package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_COMPANY;
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
import static seedu.finclient.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Set;

import seedu.finclient.logic.commands.AddCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Company;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Job;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Networth;
import seedu.finclient.model.person.Person;
import seedu.finclient.model.person.PhoneList;
import seedu.finclient.model.person.Remark;
import seedu.finclient.model.person.StockPlatform;
import seedu.finclient.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ORDER,
                        PREFIX_AMOUNT, PREFIX_PRICE, PREFIX_REMARK, PREFIX_TAG,
                        PREFIX_COMPANY, PREFIX_JOB, PREFIX_PLATFORM, PREFIX_NETWORTH);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK,
                PREFIX_COMPANY, PREFIX_JOB, PREFIX_PLATFORM, PREFIX_NETWORTH);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        PhoneList phoneList = ParserUtil.parsePhoneList(argMultimap.getAllValues(PREFIX_PHONE));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Remark remark = arePrefixesPresent(argMultimap, PREFIX_REMARK)
                ? ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get())
                : new Remark("");

        // Order
        boolean hasOrder = argMultimap.getValue(PREFIX_ORDER).isPresent();
        boolean hasAmount = argMultimap.getValue(PREFIX_AMOUNT).isPresent();
        boolean hasPrice = argMultimap.getValue(PREFIX_PRICE).isPresent();

        Order order;
        if (!hasOrder) {
            order = new Order("NONE");
        } else if (hasAmount && hasPrice) {
            order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get(),
                    argMultimap.getValue(PREFIX_AMOUNT).get(), argMultimap.getValue(PREFIX_PRICE).get());
        } else {
            throw new ParseException(Order.MESSAGE_CONSTRAINTS);
        }

        // Optional Fields
        Company company;
        Job job;
        StockPlatform stockPlatform;
        Networth networth;

        if (arePrefixesPresent(argMultimap, PREFIX_COMPANY)) {
            if (argMultimap.getValue(PREFIX_COMPANY).get().equals("delete")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            } else {
                company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
            }
        } else {
            company = new Company();
        }

        if (arePrefixesPresent(argMultimap, PREFIX_JOB)) {
            if (argMultimap.getValue(PREFIX_JOB).get().equals("delete")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            } else {
                job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
            }
        } else {
            job = new Job();
        }

        if (arePrefixesPresent(argMultimap, PREFIX_PLATFORM)) {
            if (argMultimap.getValue(PREFIX_PLATFORM).get().equals("delete")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            } else {
                stockPlatform = ParserUtil.parseStockPlatform(argMultimap.getValue(PREFIX_PLATFORM).get());
            }
        } else {
            stockPlatform = new StockPlatform();
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NETWORTH)) {
            if (argMultimap.getValue(PREFIX_NETWORTH).get().equals("delete")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            } else {
                networth = ParserUtil.parseNetworth(argMultimap.getValue(PREFIX_NETWORTH).get());
            }
        } else {
            networth = new Networth();
        }

        Person person = new Person(name, phoneList, email, address, order, remark, tagList, company, job,
                stockPlatform, networth);
        return new AddCommand(person);
    }
}
