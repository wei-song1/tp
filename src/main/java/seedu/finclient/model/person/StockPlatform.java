package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's chosen stock platform in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStockPlatform(String)}
 */
public class StockPlatform {
    public static final String MESSAGE_CONSTRAINTS =
            "Stock platform can be any name, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Constructs an {@code StockPlatform}.
     *
     * @param platformName A valid stock platform.
     */
    public StockPlatform(String platformName) {
        requireNonNull(platformName);
        checkArgument(isValidStockPlatform(platformName), MESSAGE_CONSTRAINTS);
        value = platformName;
    }

    public StockPlatform() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid stock platform.
     */
    public static boolean isValidStockPlatform(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof StockPlatform)) {
            return false;
        }

        StockPlatform otherPlatform = (StockPlatform) other;
        return value.equals(otherPlatform.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
