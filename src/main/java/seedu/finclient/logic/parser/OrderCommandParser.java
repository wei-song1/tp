package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.OrderCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.order.Order;

/**
 * Parses input arguments and creates a new OrderCommand object
 */
public class OrderCommandParser implements Parser<OrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OrderCommand
     * and returns an OrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_ORDER, PREFIX_AMOUNT, PREFIX_PRICE);

        // parse the index from the preamble
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + OrderCommand.MESSAGE_USAGE);
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ORDER, PREFIX_AMOUNT, PREFIX_PRICE);

        boolean hasOrder = argMultimap.getValue(PREFIX_ORDER).isPresent();
        boolean hasAmount = argMultimap.getValue(PREFIX_AMOUNT).isPresent();
        boolean hasPrice = argMultimap.getValue(PREFIX_PRICE).isPresent();

        Order order;
        // if user provided all three...
        if (hasOrder && hasAmount && hasPrice) {
            order = ParserUtil.parseOrder(
                    argMultimap.getValue(PREFIX_ORDER).get(),
                    argMultimap.getValue(PREFIX_AMOUNT).get(),
                    argMultimap.getValue(PREFIX_PRICE).get()
            );
        } else if (!hasOrder && !hasAmount && !hasPrice) {
            // none given → "NONE"
            order = new Order("NONE");
        } else {
            // partial: e.g. user gave order type but not amount? Throw error
            throw new ParseException("If you specify an order, you must provide all of order, amount, price.\n"
                    + OrderCommand.MESSAGE_USAGE);
        }

        return new OrderCommand(index, order);
    }
}
