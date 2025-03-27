package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS =
            "Remark can contain anything, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;
    public final Optional<LocalDateTime> timestamp;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark valid content.
     */
    public Remark(String remark) {
        this(remark, Optional.empty());
    }

    /**
     * Constructs a {@code Remark} object with the given text and optional timestamp.
     *
     * @param remark The textual content of the remark. Must not be null.
     * @param timestamp An {@code Optional} containing a {@code LocalDateTime} if the remark has an associated deadline,
     *                  or {@code Optional.empty()} if no timestamp is specified. Must not be null.
     */
    public Remark(String remark, Optional<LocalDateTime> timestamp) {
        requireNonNull(remark);
        requireNonNull(timestamp);
        this.value = remark;
        this.timestamp = timestamp;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Optional<LocalDateTime> getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return timestamp.map(t -> value + " (Due: " + t.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ")")
                .orElse(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value)
                && timestamp.equals(otherRemark.timestamp);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + timestamp.hashCode();
    }

}
