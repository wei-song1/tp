package seedu.finclient.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.RevealCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

public class RevealCommandParserTest {

    private final RevealCommandParser parser = new RevealCommandParser();

    // 1) Blank/empty input -> should throw ParseException
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevealCommand.MESSAGE_USAGE));

        assertThrows(ParseException.class, () -> parser.parse("   "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevealCommand.MESSAGE_USAGE));
    }

    // 2) Single token = "all" -> new RevealCommand() (reveal everything)
    @Test
    public void parse_allArgs_returnsRevealAllCommand() throws Exception {
        RevealCommand command = parser.parse("all");
        // A RevealCommand with no predicate/index is the "reveal all" variant
        assertEquals(new RevealCommand(), command);
    }

    // 3) Single valid index -> parse as Index
    @Test
    public void parse_singleValidIndex_returnsRevealCommandWithIndex() throws Exception {
        RevealCommand command = parser.parse("1");
        // We expect a RevealCommand(Index.fromOneBased(1))
        RevealCommand expected = new RevealCommand(Index.fromOneBased(1));
        assertEquals(expected, command);
    }

    // 4) Name keywords (single or multiple)
    @Test
    public void parse_nameKeywords_returnsRevealCommandWithNamePredicate() throws Exception {
        // Single keyword
        RevealCommand command = parser.parse("Alice");
        RevealCommand expected = new RevealCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertEquals(expected, command);

        // Multiple keywords
        RevealCommand command2 = parser.parse("Alice Bob");
        RevealCommand expected2 = new RevealCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertEquals(expected2, command2);
    }
}
