/**
 * A single item in the chatbot's task list.
 * Currently stores only a description.
 */
public class Task {
    private final String description;

    /**
     * Creates a task with the given description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Returns the task description.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the description, which is how the task is shown to the user.
     *
     * @return the description text
     */
    @Override
    public String toString() {
        return description;
    }
}
