/**
 * Adds a task whose description is the full user input line.
 */
public class AddCommand implements Command {
    private final String description;

    /**
     * Creates a command that will add a task with the given description.
     *
     * @param description text of the task to add
     */
    public AddCommand(String description) {
        this.description = description;
    }

    /**
     * Adds the task to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Task(description);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
