package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

public class Networth {
    public static final String MESSAGE_CONSTRAINTS =
            "Networth can take any positive values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Networth(String networthAmount) {
        requireNonNull(networthAmount);
        if (isBracket(networthAmount)) {
            value = networthAmount;
        } else {
            checkArgument(isValidNetworth(networthAmount), MESSAGE_CONSTRAINTS);
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
}
