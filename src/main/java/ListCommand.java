/**
 * Shows every task currently in the list.
 * This command does not take arguments.
 */
public class ListCommand implements Command {
    /**
     * Parses {@code list} with no extra text.
     *
     * @param arguments text after the command word
     * @return a list command
     * @throws EkudException if extra arguments are present
     */
    public static ListCommand parse(String arguments) throws EkudException {
        Parser.requireNoArguments("list", arguments);
        return new ListCommand();
    }

    private ListCommand() {
    }

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
