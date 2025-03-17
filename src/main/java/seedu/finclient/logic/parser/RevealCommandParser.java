package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.commons.util.StringUtil;
import seedu.finclient.logic.commands.RevealCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new RevealCommand object
 */
public class RevealCommandParser implements Parser<RevealCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RevealCommand
     * and returns a RevealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RevealCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevealCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = trimmedArgs.split("\\s+");

        // 1) "reveal all" -> Reveal everything
        if (splitArgs.length == 1 && splitArgs[0].equalsIgnoreCase("all")) {
            return new RevealCommand();
        }

        // 2) Check if the token is a valid one-based index
        if (splitArgs.length == 1 && StringUtil.isNonZeroUnsignedInteger(splitArgs[0])) {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            return new RevealCommand(index);
        }

        // 3) Otherwise, treat them as name keywords
        return new RevealCommand(new NameContainsKeywordsPredicate(Arrays.asList(splitArgs)));
    }
}
