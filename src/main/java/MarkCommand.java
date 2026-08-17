/**
 * Marks a task as done, using the 1-based number shown by {@code list}.
 */
public class MarkCommand implements Command {
    private final int oneBasedIndex;

    /**
     * Creates a command that will mark the given task as done.
     *
     * @param oneBasedIndex task number as shown in the list, starting from 1
     */
    public MarkCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    /**
     * Marks the selected task as done and shows a confirmation.
     *
     * @param tasks the list containing the task
     * @param ui    used to show the confirmation
     * @throws EkudException if the task number does not exist
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws EkudException {
        Task task = tasks.getByOneBasedIndex(oneBasedIndex);
        task.markAsDone();
        ui.showMarked(task);
    }
}
