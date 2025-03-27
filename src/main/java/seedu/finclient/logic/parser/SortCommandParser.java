package seedu.finclient.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.finclient.logic.commands.SortCommand;
import seedu.finclient.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String criteria = args.trim();

        if (!SortCommand.isValidCriteria(criteria)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(criteria);
    }
}
