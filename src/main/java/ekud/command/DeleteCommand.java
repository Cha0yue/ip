package ekud.command;

import ekud.EkudException;
import ekud.parser.Parser;
import ekud.storage.Storage;
import ekud.task.Task;
import ekud.task.TaskList;
import ekud.ui.Ui;

/**
 * Deletes a task, using the 1-based number shown by {@code list}.
 */
public class DeleteCommand implements Command {
    private final int oneBasedIndex;

    /**
     * Parses {@code delete INDEX}.
     *
     * @param arguments text after the command word
     * @return a delete command for that task number
     * @throws EkudException if the index is missing, not an integer, or extra text is present
     */
    public static DeleteCommand parse(String arguments) throws EkudException {
        return new DeleteCommand(Parser.parseOneBasedIndex("delete", arguments));
    }

    /**
     * Creates a command that will delete the given task.
     *
     * @param oneBasedIndex task number as shown in the list, starting from 1
     */
    private DeleteCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    /**
     * Removes the selected task, saves the list, and shows a confirmation.
     *
     * @param tasks   the list containing the task
     * @param ui      used to show the confirmation
     * @param storage used to persist the updated list
     * @throws EkudException if the task number does not exist or the list cannot be saved
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EkudException {
        Task removed = tasks.removeByOneBasedIndex(oneBasedIndex);
        storage.save(tasks);
        ui.showDeleted(removed, tasks.size());
    }
}
