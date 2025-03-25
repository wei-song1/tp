package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

public class Job {
    public static final String MESSAGE_CONSTRAINTS =
            "Job can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Job(String jobTitle) {
        requireNonNull(jobTitle);
        checkArgument(isValidJob(jobTitle), MESSAGE_CONSTRAINTS);
        value = jobTitle;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidJob(String test) {
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
        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return value.equals(otherJob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
