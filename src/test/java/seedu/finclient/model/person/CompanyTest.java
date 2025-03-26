package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null email
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // blank email
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only

        // valid email
        assertTrue(Company.isValidCompany("Facebook"));
        assertTrue(Company.isValidCompany("Meta, Facebook"));
        assertTrue(Company.isValidCompany("Google"));
        assertTrue(Company.isValidCompany("Alphabet Inc, Google"));
        assertTrue(Company.isValidCompany("true"));
        assertTrue(Company.isValidCompany("false"));
        assertTrue(Company.isValidCompany("$"));
        assertTrue(Company.isValidCompany("404 Not Found"));
        assertTrue(Company.isValidCompany("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Company.isValidCompany("PeterJack.1190@example.com")); // period in local part
        assertTrue(Company.isValidCompany("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Company.isValidCompany("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Company.isValidCompany("a@bc")); // minimal
        assertTrue(Company.isValidCompany("test@localhost")); // alphabets only
        assertTrue(Company.isValidCompany("123@145")); // numeric local part and domain name
        assertTrue(Company.isValidCompany("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Company.isValidCompany("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Company.isValidCompany("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Company.isValidCompany("e1234567@u.nus.edu")); // more than one period in domain
        assertTrue(Company.isValidCompany("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Company company = new Company("valid@email");

        // same values -> returns true
        assertTrue(company.equals(new Company("valid@email")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("other.valid@email")));
    }
}
