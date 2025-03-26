package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Job(null));
    }

    @Test
    public void constructor_invalidJob_throwsIllegalArgumentException() {
        String invalidJob = "";
        assertThrows(IllegalArgumentException.class, () -> new Job(invalidJob));
    }

    @Test
    public void isValidJob() {
        // null job
        assertThrows(NullPointerException.class, () -> Job.isValidJob(null));

        // blank job
        assertFalse(Job.isValidJob("")); // empty string
        assertFalse(Job.isValidJob(" ")); // spaces only

        // valid job
        assertTrue(Job.isValidJob("Software Engineer"));
        assertTrue(Job.isValidJob("Senior Product Manager"));
        assertTrue(Job.isValidJob("Chief Executive Officer"));
        assertTrue(Job.isValidJob("Intern, Google"));
        assertTrue(Job.isValidJob("true"));
        assertTrue(Job.isValidJob("false"));
        assertTrue(Job.isValidJob("$"));
        assertTrue(Job.isValidJob("404 Error Consultant"));
        assertTrue(Job.isValidJob("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Job.isValidJob("PeterJack.1190@example.com")); // period in local part
        assertTrue(Job.isValidJob("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Job.isValidJob("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Job.isValidJob("a@bc")); // minimal
        assertTrue(Job.isValidJob("test@localhost")); // alphabets only
        assertTrue(Job.isValidJob("123@145")); // numeric local part and domain name
        assertTrue(Job.isValidJob("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Job.isValidJob("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Job.isValidJob("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Job.isValidJob("e1234567@u.nus.edu")); // more than one period in domain
        assertTrue(Job.isValidJob("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Job job = new Job("valid@email");

        // same values -> returns true
        assertTrue(job.equals(new Job("valid@email")));

        // same object -> returns true
        assertTrue(job.equals(job));

        // null -> returns false
        assertFalse(job.equals(null));

        // different types -> returns false
        assertFalse(job.equals(5.0f));

        // different values -> returns false
        assertFalse(job.equals(new Job("other.valid@email")));
    }
}
