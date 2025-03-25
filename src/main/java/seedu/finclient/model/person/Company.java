package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

public class Company {
    public static final String MESSAGE_CONSTRAINTS =
            "Company name can be any name, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Company(String companyName) {
        requireNonNull(companyName);
        checkArgument(isValidCompany(companyName), MESSAGE_CONSTRAINTS);
        value = companyName;
    }

    public Company() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid company.
     */
    public static boolean isValidCompany(String test) {
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
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return value.equals(otherCompany.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
