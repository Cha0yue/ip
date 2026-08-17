/**
 * Adds a {@link Todo} to the task list.
 */
public class TodoCommand implements Command {
    private final String description;

    /**
     * Creates a command that will add a todo with the given description.
     *
     * @param description text of the todo to add
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Adds the todo to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Todo(description);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
