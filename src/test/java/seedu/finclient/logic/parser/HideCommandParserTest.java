package seedu.finclient.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.HideCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

public class HideCommandParserTest {

    private final HideCommandParser parser = new HideCommandParser();

    // 1) Blank/empty input -> should throw ParseException
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
        assertThrows(ParseException.class, () -> parser.parse("   "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
    }

    // 2) Single token = "all" -> new HideCommand() (hide everything)
    @Test
    public void parse_allArgs_returnsHideAllCommand() throws Exception {
        HideCommand command = parser.parse("all");
        // A HideCommand with no predicate/index is the "hide all" variant
        assertEquals(new HideCommand(), command);
    }

    // 3) Single valid index -> parse as Index
    @Test
    public void parse_singleValidIndex_returnsHideCommandWithIndex() throws Exception {
        HideCommand command = parser.parse("1");
        // We expect a HideCommand(Index.fromOneBased(1))
        HideCommand expected = new HideCommand(Index.fromOneBased(1));
        assertEquals(expected, command);
    }

    // 4) Name keywords (single or multiple)
    @Test
    public void parse_nameKeywords_returnsHideCommandWithNamePredicate() throws Exception {
        // Single keyword
        HideCommand command = parser.parse("Alice");
        HideCommand expected = new HideCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertEquals(expected, command);

        // Multiple keywords
        HideCommand command2 = parser.parse("Alice Bob");
        HideCommand expected2 = new HideCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertEquals(expected2, command2);
    }
}
