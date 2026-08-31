package ekud;

import ekud.command.Command;
import ekud.command.DeleteCommand;
import ekud.command.MarkCommand;
import ekud.command.TaskCreatingCommand;
import ekud.command.UnmarkCommand;
import ekud.parser.Parser;
import ekud.storage.Storage;
import ekud.task.TaskList;
import ekud.ui.Ui;

/**
 * Entry point for the Ekud chatbot.
 * On startup, it loads any saved tasks. The CLI greets the user and reads
 * commands until {@code bye}. The GUI calls {@link #getResponse} once per
 * message. Command parsing, disk storage, the in-memory list, and user-facing
 * text are delegated to {@link Parser}, {@link Storage}, {@link TaskList},
 * and {@link Ui} respectively.
 */
public class Ekud {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;
    /**
     * Set if the save file exists but cannot be read. Shown after the welcome
     * banner so the greeting is not interrupted by a raw constructor error.
     */
    private final String startupError;
    private boolean isExit;
    private String lastCommandType;

    /**
     * Creates a chatbot and loads tasks from {@link Storage#DEFAULT_PATH}.
     * If the file is missing, the list starts empty. If the file cannot be
     * read, the list also starts empty and {@link #startupError} is set.
     */
    public Ekud() {
        this(new Ui(), new Storage(Storage.DEFAULT_PATH));
    }

    /**
     * Creates a chatbot with the given UI and storage.
     * The GUI and tests use this so they can avoid the terminal and the default
     * save file.
     *
     * @param ui      where replies are sent
     * @param storage where the task list is persisted
     */
    public Ekud(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        this.isExit = false;
        this.lastCommandType = "";
        String error = null;
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (EkudException e) {
            error = e.getMessage();
            loaded = new TaskList();
        }
        this.tasks = loaded;
        this.startupError = error;
    }

    /**
     * Starts the command-line chatbot: prints a greeting (including a dad joke),
     * then reads and runs commands until the user types {@code bye}.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Ekud().run();
    }

    /**
     * Runs the welcome screen and the main command loop.
     */
    public void run() {
        ui.showWelcome();
        if (startupError != null) {
            ui.showError(startupError);
        }
        boolean isRunning = true;
        while (isRunning) {
            ui.showPrompt();
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);
                isRunning = !command.isExit();
            } catch (EkudException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    /**
     * Returns the greeting text for the first GUI dialog box, including any
     * startup error about the save file.
     *
     * @return welcome text without the ASCII banner
     */
    public String getWelcomeMessage() {
        ui.showWelcome();
        if (startupError == null) {
            return ui.getLastMessage();
        }
        String welcome = ui.getLastMessage();
        ui.showError(startupError);
        return welcome + "\n\n" + ui.getLastMessage();
    }

    /**
     * Parses and executes one command, then returns the reply text for the GUI.
     *
     * @param input the line typed by the user
     * @return the reply to show in a dialog box
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(tasks, ui, storage);
            isExit = command.isExit();
            lastCommandType = toDialogStyle(command);
            return ui.getLastMessage();
        } catch (EkudException e) {
            lastCommandType = "";
            ui.showError(e.getMessage());
            return ui.getLastMessage();
        }
    }

    /**
     * Returns whether the last {@link #getResponse} was the exit command.
     *
     * @return {@code true} if the GUI should close
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Returns a style key for the last successful command, used to tint reply
     * bubbles. Empty when the last input was invalid or unstyled.
     *
     * @return {@code AddCommand}, {@code ChangeMarkCommand}, {@code DeleteCommand},
     *         or an empty string
     */
    public String getCommandType() {
        return lastCommandType;
    }

    /**
     * Maps a command to the dialog-box style names used in the JavaFX tutorial.
     *
     * @param command the command that just ran
     * @return a style key, or an empty string
     */
    private static String toDialogStyle(Command command) {
        if (command instanceof TaskCreatingCommand) {
            return "AddCommand";
        }
        if (command instanceof MarkCommand || command instanceof UnmarkCommand) {
            return "ChangeMarkCommand";
        }
        if (command instanceof DeleteCommand) {
            return "DeleteCommand";
        }
        return "";
    }
}
