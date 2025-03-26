package seedu.finclient.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.OrderCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.order.Order;

public class OrderCommandParserTest {

    private final OrderCommandParser parser = new OrderCommandParser();

    // 1) No index provided -> should throw ParseException
    @Test
    public void parse_noIndexProvided_throwsParseException() {
        // e.g. we have prefixes but no index in the preamble
        assertThrows(ParseException.class, () -> parser.parse("o/buy am/100 at/50"),
                "Must provide an index! \n" + OrderCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, () -> parser.parse("  o/sell am/50 at/10.50"),
                "Must provide an index! \n" + OrderCommand.MESSAGE_USAGE);
    }

    // 2) Index provided, but partial prefixes -> should throw ParseException
    @Test
    public void parse_partialPrefixesProvided_throwsParseException() {
        // If user provides just one or two of the three (o/ a/ p/) => should fail
        assertThrows(ParseException.class, () -> parser.parse("1 o/buy am/100"),
                "If you specify an order, you must provide all of order, amount, price.");
        assertThrows(ParseException.class, () -> parser.parse("2 o/sell at/3.33"),
                "If you specify an order, you must provide all of order, amount, price.");
        assertThrows(ParseException.class, () -> parser.parse("3 am/200 at/100.0"),
                "If you specify an order, you must provide all of order, amount, price.");
    }

    // 3) Valid index only, no prefixes -> returns OrderCommand with "NONE"
    @Test
    public void parse_indexOnlyProvided_returnsOrderCommandWithNone() throws Exception {
        // e.g. "3" means index=3, order=NONE
        OrderCommand expected = new OrderCommand(Index.fromOneBased(3), new Order("NONE"));
        assertEquals(expected, parser.parse("3"));
    }

    // 4) All prefixes provided -> should create valid Order object
    @Test
    public void parse_allPrefixesProvided_returnsOrderCommand() throws Exception {
        Order expectedOrder = new Order(Order.OrderType.BUY, "50", 100);
        OrderCommand expectedCommand = new OrderCommand(Index.fromOneBased(2), expectedOrder);

        assertEquals(expectedCommand, parser.parse("2 o/buy am/100 at/50"));
    }

    // 5) Invalid index in preamble -> should throw ParseException
    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("abc o/buy am/100 at/50"),
                "For input string: \"abc\"");
    }
}
