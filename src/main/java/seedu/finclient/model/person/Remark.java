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
    public static final String MESSAGE_INVALID_TIMESTAMP = "The timestamp is invalid";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public final String value;
    public final Optional<LocalDateTime> timestamp;

    /**
     * Constructor used when timestamp is explicitly parsed (e.g. by command parser).
     */
    public Remark(String remark, Optional<LocalDateTime> timestamp) {
        requireNonNull(remark);
        requireNonNull(timestamp);
        this.value = remark;
        this.timestamp = timestamp;
    }

    public String getDisplayText() {
        return value.isEmpty() ? "Description is empty" : value;
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
        return timestamp.map(t -> value + " (At: " + t.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ")")
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

    /**
     * Compares this remark with another remark based on the timestamp.
     * If both have timestamps, it compares them.
     * If only one has a timestamp, it is considered "less than" the other.
     * If neither has a timestamp, they are considered equal.
     */
    public int compareTo(Remark other) {
        requireNonNull(other);
        if (this.timestamp.isPresent() && other.timestamp.isPresent()) {
            return this.timestamp.get().compareTo(other.timestamp.get());
        } else if (this.timestamp.isPresent()) {
            return -1;
        } else if (other.timestamp.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode() + timestamp.hashCode();
    }

}
