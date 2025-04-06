package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.finclient.commons.core.index.Index;
import seedu.finclient.commons.util.StringUtil;
import seedu.finclient.logic.commands.HideCommand;
import seedu.finclient.logic.commands.RevealCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new HideCommand object
 */
public class HideCommandParser implements Parser<HideCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HideCommand
     * and returns a HideCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HideCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = trimmedArgs.split("\\s+");

        // 1) "hide all" -> Hide everything
        if (splitArgs.length == 1 && splitArgs[0].equalsIgnoreCase("all")) {
            return new HideCommand();
        }

        // 2) Check if the token is a valid one-based index
        if (splitArgs.length == 1 && StringUtil.isNonZeroUnsignedInteger(splitArgs[0])) {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            return new HideCommand(index);
        }

        // 3) Otherwise, ensure they contain only letters and treat as keywords
        for (String arg : splitArgs) {
            if (!arg.matches("[a-zA-Z]+")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevealCommand.MESSAGE_USAGE));
            }
        }

        return new HideCommand(new NameContainsKeywordsPredicate(Arrays.asList(splitArgs)));
    }
}
