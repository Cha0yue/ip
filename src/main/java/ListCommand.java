/**
 * Shows every task currently in the list.
 * This command does not take arguments; the {@link Parser} rejects extra text.
 */
public class ListCommand implements Command {
    /**
     * Displays the tasks through the UI.
     *
     * @param tasks the list to display
     * @param ui    used to show the list
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showTaskList(tasks);
    }
}
