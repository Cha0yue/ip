/**
 * Adds a {@link Deadline} to the task list.
 */
public class DeadlineCommand implements Command {
    private final String description;
    private final String by;

    /**
     * Creates a command that will add a deadline.
     *
     * @param description text of the deadline to add
     * @param by          due date/time as typed by the user
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Adds the deadline to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
