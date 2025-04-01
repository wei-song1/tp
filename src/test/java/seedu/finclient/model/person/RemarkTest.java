package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void testCompareTo_bothWithoutDeadline() {
        Remark remark1 = new Remark("Hello");
        Remark remark2 = new Remark("Hello");
        assertTrue(remark1.compareTo(remark2) == 0);
    }

    @Test
    public void testCompareTo_oneWithAndOneWithoutDeadline() {
        LocalDateTime dateTime = LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay();
        Remark remark1 = new Remark("Deadline 1", Optional.of(dateTime));
        Remark remark2 = new Remark("Hello");
        assertTrue(remark1.compareTo(remark2) == -1);
    }

    @Test
    public void testCompareTo_bothWithDeadline() {
        LocalDateTime dateTime = LocalDate.of(2025, Month.APRIL, 18).atStartOfDay();
        LocalDateTime anotherDateTime = LocalDate.of(2025, Month.MAY, 19).atStartOfDay();

        // test same datetime
        Remark remark1 = new Remark("Deadline 1", Optional.of(dateTime));
        Remark remark2 = new Remark("Deadline 2", Optional.of(dateTime));
        assertTrue(remark1.compareTo(remark2) == 0);

        // test different datetime
        Remark remark3 = new Remark("Deadline 1", Optional.of(dateTime));
        Remark remark4 = new Remark("Deadline 2", Optional.of(anotherDateTime));
        assertTrue(remark3.compareTo(remark4) == -1);

        // test different datetime
        Remark remark5 = new Remark("Deadline 1", Optional.of(anotherDateTime));
        Remark remark6 = new Remark("Deadline 2", Optional.of(dateTime));
        assertTrue(remark5.compareTo(remark6) == 1);
    }


}
