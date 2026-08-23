package ekud.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import ekud.EkudException;

/**
 * Tests {@link TaskList} add, lookup, delete, and date filtering.
 */
public class TaskListTest {
    @Test
    public void add_increasesSize() {
        TaskList tasks = new TaskList();
        assertTrue(tasks.isEmpty());
        tasks.add(new Todo("read book"));
        assertEquals(1, tasks.size());
        assertFalse(tasks.isEmpty());
        assertEquals("read book", tasks.get(0).getDescription());
    }

    @Test
    public void getByOneBasedIndex_validNumber_returnsTask() throws EkudException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first"));
        tasks.add(new Todo("second"));
        assertEquals("second", tasks.getByOneBasedIndex(2).getDescription());
    }

    @Test
    public void getByOneBasedIndex_outOfRange_throwsEkudException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("only"));
        EkudException tooSmall = assertThrows(EkudException.class, () -> tasks.getByOneBasedIndex(0));
        assertEquals("Task number 0 does not exist.", tooSmall.getMessage());
        EkudException tooLarge = assertThrows(EkudException.class, () -> tasks.getByOneBasedIndex(2));
        assertEquals("Task number 2 does not exist.", tooLarge.getMessage());
    }

    @Test
    public void removeByOneBasedIndex_shiftsLaterTasks() throws EkudException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first"));
        tasks.add(new Todo("second"));
        tasks.add(new Todo("third"));
        Task removed = tasks.removeByOneBasedIndex(2);
        assertEquals("second", removed.getDescription());
        assertEquals(2, tasks.size());
        assertEquals("third", tasks.getByOneBasedIndex(2).getDescription());
    }

    @Test
    public void findOccurringOn_includesDeadlinesAndEventsOnly() throws EkudException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("ignore me"));
        tasks.add(new Deadline("return book", TaskDateTime.parse("2019-12-02")));
        tasks.add(new Event("camp",
                TaskDateTime.parse("2019-12-01"),
                TaskDateTime.parse("2019-12-03")));
        tasks.add(new Deadline("other", TaskDateTime.parse("2019-12-04")));

        List<Task> matches = tasks.findOccurringOn(LocalDate.of(2019, 12, 2));
        assertEquals(2, matches.size());
        assertEquals("return book", matches.get(0).getDescription());
        assertEquals("camp", matches.get(1).getDescription());
    }

    @Test
    public void constructor_copiesLoadedTasks() {
        List<Task> loaded = List.of(new Todo("saved"));
        TaskList tasks = new TaskList(loaded);
        assertEquals(1, tasks.size());
        tasks.add(new Todo("extra"));
        assertEquals(1, loaded.size());
    }
}
