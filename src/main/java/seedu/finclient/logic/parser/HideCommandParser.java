package seedu.finclient.logic.parser;

import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.finclient.logic.commands.HideCommand;
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

        String[] nameKeywords = trimmedArgs.split("\\s+");

        // if there is an "all", return a HideCommand with no predicate
        if (nameKeywords.length == 1 && nameKeywords[0].equalsIgnoreCase("all")) {
            return new HideCommand();
        } else {
            return new HideCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
    }
}
