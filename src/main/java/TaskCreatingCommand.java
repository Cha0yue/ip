/**
 * An add command that can build a {@link Task} from its already-parsed arguments.
 * {@link Storage} uses this when loading the save file so it can reuse
 * {@link Parser} without calling {@link #execute}, which would print
 * "added" messages for every saved task.
 */
public interface TaskCreatingCommand extends Command {
    /**
     * Builds the task this command would add, without changing the list or UI.
     *
     * @return a new task matching the parsed arguments
     */
    Task createTask();
}
