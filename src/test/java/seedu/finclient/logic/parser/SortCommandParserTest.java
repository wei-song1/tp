package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_SORT_CRITERIA_NAME;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.finclient.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        SortCommand expectedSortCommand = new SortCommand(VALID_SORT_CRITERIA_NAME);
        // valid criteria
        assertParseSuccess(parser, " c/" + VALID_SORT_CRITERIA_NAME,
                expectedSortCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        // missing criteria prefix
        assertParseFailure(parser, "", expectedMessage);
    }
}
