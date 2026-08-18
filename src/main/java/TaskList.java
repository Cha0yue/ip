import java.util.ArrayList;

/**
 * In-memory list of {@link Task} objects.
 * There is no hard size limit.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param index position in the list, starting from 0
     * @return the task at that position
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the task at the given 1-based number, matching {@code list} output.
     *
     * @param index task number as shown to the user, starting from 1
     * @return the task at that number
     * @throws EkudException if {@code index} is out of range
     */
    public Task getByOneBasedIndex(int index) throws EkudException {
        if (index < 1 || index > tasks.size()) {
            throw new EkudException("Task number " + index + " does not exist.");
        }
        return tasks.get(index - 1);
    }

    /**
     * Removes and returns the task at the given 1-based number.
     * Later tasks shift down so the remaining numbers stay consecutive.
     *
     * @param index task number as shown to the user, starting from 1
     * @return the task that was removed
     * @throws EkudException if {@code index} is out of range
     */
    public Task removeByOneBasedIndex(int index) throws EkudException {
        Task task = getByOneBasedIndex(index);
        tasks.remove(index - 1);
        return task;
    }

    /**
     * Returns how many tasks are currently stored.
     *
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether the list has no tasks.
     *
     * @return {@code true} if the list is empty
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
