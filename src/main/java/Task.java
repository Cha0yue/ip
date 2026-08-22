/**
 * A single item in the chatbot's task list.
 * Concrete subclasses ({@link Todo}, {@link Deadline}, {@link Event}) add any
 * date/time details. Dates are stored as the user typed them for now.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description text describing the task
     */
    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description, without type or date details.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return {@code true} if the task is done
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the letter that identifies this task type, for example {@code T}.
     *
     * @return the type icon
     */
    public abstract String getTypeIcon();

    /**
     * Returns {@code X} if done, or a space if not done, for the status box.
     *
     * @return the status icon character as a string
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Extra details such as a deadline or event times. Empty for todos.
     *
     * @return a suffix to append after the description, or an empty string
     */
    protected String extraDetails() {
        return "";
    }

    /**
     * Returns the add-command text that would recreate this task, without the
     * done flag. {@link Storage} prefixes {@code 0} or {@code 1} when saving.
     *
     * @return a line such as {@code todo borrow book}
     */
    public abstract String toCommandString();

    /**
     * Returns the task with type and status boxes, for example
     * {@code [T][ ] borrow book}.
     *
     * @return the formatted task
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + extraDetails();
    }
}
