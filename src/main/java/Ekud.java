/**
 * Entry point for the Ekud chatbot.
 * On startup it loads any saved tasks, greets the user, then reads commands
 * until {@code bye}. Command parsing, disk storage, the in-memory list, and
 * printing are delegated to {@link Parser}, {@link Storage}, {@link TaskList},
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

    /**
     * Creates a chatbot and loads tasks from {@link Storage#DEFAULT_PATH}.
     * If the file is missing, the list starts empty. If the file cannot be
     * read, the list also starts empty and {@link #startupError} is set.
     */
    public Ekud() {
        this.ui = new Ui();
        this.storage = new Storage(Storage.DEFAULT_PATH);
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
     * Starts the chatbot: prints a greeting (including a dad joke), then
     * reads and runs commands until the user types {@code bye}.
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
}
