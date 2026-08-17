/**
 * Marks a task as not done, using the 1-based number shown by {@code list}.
 */
public class UnmarkCommand implements Command {
    private final int oneBasedIndex;

    /**
     * Creates a command that will mark the given task as not done.
     *
     * @param oneBasedIndex task number as shown in the list, starting from 1
     */
    public UnmarkCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    /**
     * Marks the selected task as not done and shows a confirmation.
     *
     * @param tasks the list containing the task
     * @param ui    used to show the confirmation
     * @throws EkudException if the task number does not exist
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws EkudException {
        Task task = tasks.getByOneBasedIndex(oneBasedIndex);
        task.markAsNotDone();
        ui.showUnmarked(task);
    }
}
