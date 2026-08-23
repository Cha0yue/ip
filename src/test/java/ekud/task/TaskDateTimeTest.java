package ekud.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ekud.EkudException;

/**
 * Tests {@link TaskDateTime} parsing, display, and save formats.
 */
public class TaskDateTimeTest {
    @Test
    public void parse_isoDate_success() throws EkudException {
        TaskDateTime value = TaskDateTime.parse("2019-12-02");
        assertEquals(LocalDate.of(2019, 12, 2), value.toLocalDate());
        assertFalse(value.hasTime());
        assertEquals("Dec 02 2019", value.toDisplayString());
        assertEquals("2019-12-02", value.toSaveString());
    }

    @Test
    public void parse_slashDate_isDayMonthYear() throws EkudException {
        TaskDateTime value = TaskDateTime.parse("2/12/2019");
        assertEquals(LocalDate.of(2019, 12, 2), value.toLocalDate());
        assertEquals("2019-12-02", value.toSaveString());
    }

    @Test
    public void parse_isoDateWithHHmm_includesTime() throws EkudException {
        TaskDateTime value = TaskDateTime.parse("2019-12-02 1800");
        assertTrue(value.hasTime());
        assertEquals(LocalTime.of(18, 0), value.toLocalTime());
        assertEquals("Dec 02 2019, 6:00 PM", value.toDisplayString());
        assertEquals("2019-12-02 1800", value.toSaveString());
    }

    @Test
    public void parse_slashDateWithColonTime_includesTime() throws EkudException {
        TaskDateTime value = TaskDateTime.parse("2/12/2019 18:00");
        assertEquals(LocalDate.of(2019, 12, 2), value.toLocalDate());
        assertEquals(LocalTime.of(18, 0), value.toLocalTime());
        assertEquals("2019-12-02 1800", value.toSaveString());
    }

    @Test
    public void occursOn_sameCalendarDate_returnsTrue() throws EkudException {
        TaskDateTime value = TaskDateTime.parse("2019-12-02 1800");
        assertTrue(value.occursOn(LocalDate.of(2019, 12, 2)));
        assertFalse(value.occursOn(LocalDate.of(2019, 12, 3)));
    }

    @Test
    public void parse_blank_throwsEkudException() {
        assertThrows(EkudException.class, () -> TaskDateTime.parse(""));
        assertThrows(EkudException.class, () -> TaskDateTime.parse("   "));
        assertThrows(EkudException.class, () -> TaskDateTime.parse(null));
    }

    @Test
    public void parse_unsupportedFormat_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> TaskDateTime.parse("next Friday"));
        assertEquals(
                "Please use a date such as 2019-12-02 or 2/12/2019, optionally with a time (1800 or 18:00).",
                exception.getMessage());
    }
}
