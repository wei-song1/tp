package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.finclient.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finclient.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finclient.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.RemarkCommand;
import seedu.finclient.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_withValidTimestamp_success() {
        String timestampStr = "2024-12-25 15:30";
        LocalDateTime expectedTimestamp = LocalDateTime.of(2024, 12, 25, 15, 30);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark
                + " " + PREFIX_TIMESTAMP + timestampStr;

        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(nonEmptyRemark, java.util.Optional.of(expectedTimestamp)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_withInvalidTimestamp_failure() {
        String invalidTimestamp = "25-12-2024 15:30";
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark
                + " " + PREFIX_TIMESTAMP + invalidTimestamp;

        assertParseFailure(parser, userInput, "Invalid date/time format. Use yyyy-MM-dd HH:mm");
    }
    @Test
    public void parse_withEmptyTimestamp_failure() {
        String emptyTimestamp = "";
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark
                + " " + PREFIX_TIMESTAMP + emptyTimestamp;

        assertParseFailure(parser, userInput, "Invalid date/time format. Use yyyy-MM-dd HH:mm");
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
