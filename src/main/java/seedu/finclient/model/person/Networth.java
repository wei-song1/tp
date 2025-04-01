package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.finclient.commons.core.LogsCenter;

/**
 * Represents a Person's networth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNetworth(String)}
 */
public class Networth {
    public static final String MESSAGE_CONSTRAINTS =
            "Networth can take any positive values, should be smaller than 2,147,483,647, "
                    + "should not contain any decimal points, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs an {@code Networth}.
     *
     * @param networthAmount A valid networth amount, either in number or the bracket name.
     */
    public Networth(String networthAmount) {
        requireNonNull(networthAmount);
        if (isBracket(networthAmount)) {
            logger.info("Networth inputted is bracket/special input");
            value = networthAmount;
        } else {
            checkArgument(isValidNetworth(networthAmount), MESSAGE_CONSTRAINTS);
            checkArgument(isValidAmount(networthAmount), MESSAGE_CONSTRAINTS);
            logger.info("Networth inputted is a number: " + networthAmount);
            value = getNetworthBracket(networthAmount);
        }
    }

    public Networth() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid networth amount.
     */
    public static boolean isValidNetworth(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid integer amount.
     */
    public static boolean isValidAmount(String test) {
        boolean isValid = false;
        try {
            isValid = Long.parseLong(test) >= 0 && Long.parseLong(test) < 2147483647;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Checks if the string input is the bracket name instead.
     *
     * @param test A valid bracket name or special name.
     * @return true if it's the bracket name or a special name.
     */
    public static boolean isBracket(String test) {
        return test.equals("< $100k")
                || test.equals("$100k - $250k")
                || test.equals("$250k - $500k")
                || test.equals("$500k - $1 million")
                || test.equals("$1 million - $5 million")
                || test.equals("> $5 million")
                || test.equals("Hidden")
                || test.equals("delete");
    }

    /**
     * Returns bucket that customer belongs to based on how much their networth is
     *
     * @param networthAmount Integer numbers with no comma, dashes or other symbol
     * @return String of the bucket they belong in
     */
    public String getNetworthBracket(String networthAmount) {
        int networth = Integer.parseInt(networthAmount);
        if (networth < 100000) {
            return "< $100k";
        } else if (networth >= 100000 && networth < 250000) {
            return "$100k - $250k";
        } else if (networth >= 250000 && networth < 500000) {
            return "$250k - $500k";
        } else if (networth >= 500000 && networth < 1000000) {
            return "$500k - $1 million";
        } else if (networth >= 1000000 && networth < 5000000) {
            return "$1 million - $5 million";
        } else {
            return "> $5 million";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Networth)) {
            return false;
        }

        Networth otherNetworth = (Networth) other;
        return value.equals(otherNetworth.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    /**
     * Compares this networth with another networth.
     */
    public int compareTo(Networth other) {
        List<String> order = Arrays.asList("< $100k", "$100k - $250k", "$250k - $500k", "$500k - $1 million",
                "$1 million - $5 million", "> $5 million");
        requireNonNull(other);
        return Integer.compare(order.indexOf(this.value), order.indexOf(other.value));
    }
}
