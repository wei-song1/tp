package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.finclient.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parseEmptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseCorrectArg_success() {
        assertParseSuccess(parser, "name", new SortCommand("name"));
        assertParseSuccess(parser, "amount", new SortCommand("amount"));
        assertParseSuccess(parser, "networth", new SortCommand("networth"));
        assertParseSuccess(parser, "price", new SortCommand("price"));
    }

    @Test
    public void parseInvalidArg_throwsParseException() {
        // invalid criteria
        assertParseFailure(parser, "namee", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "amountt", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "networtht", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "pricet", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        // multiple criteria
        assertParseFailure(parser, "name amount", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "name networth", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "name price", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
