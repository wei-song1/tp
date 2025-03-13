package seedu.finclient.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.person.Person;

public class SampleDataUtilTest {
    @Test
    public void getSamplePersons_returnsCorrectNumber() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length, "Sample persons count should be 6");
    }
}
