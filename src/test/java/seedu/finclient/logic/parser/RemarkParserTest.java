package seedu.finclient.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.person.Remark;

public class RemarkParserTest {

    @Test
    public void parse_validTimestamp_success() {
        Remark remark = new Remark("Christmas Party by/2024-12-25 15:30");
        assertTrue(remark.getTimestamp().isPresent());
        assertEquals(LocalDateTime.of(2024, 12, 25, 15, 30), remark.getTimestamp().get());
    }

    @Test
    public void parse_invalidTimestamp_ignored() {
        Remark remark = new Remark("Wrong format by/25-12-2024 15:30");
        assertFalse(remark.getTimestamp().isPresent());
    }

    @Test
    public void parse_noTimestamp_stillValid() {
        Remark remark = new Remark("Just a comment");
        assertFalse(remark.getTimestamp().isPresent());
        assertEquals("Just a comment", remark.value);
    }

    @Test
    public void parse_emptyContentWithTimestamp_setsEmptyContentButParsesTimestamp() {
        Remark remark = new Remark("by/2025-04-01 12:00");
        assertTrue(remark.getTimestamp().isPresent());
        assertEquals(LocalDateTime.of(2025, 4, 1, 12, 0), remark.getTimestamp().get());
        assertEquals("", remark.value);
    }
}
