package ekud.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import ekud.task.Task;
import ekud.task.TaskList;

/**
 * Handles all interaction with the user: reading typed input and printing
 * messages. Keeping I/O here keeps {@link ekud.Ekud} and the commands free of
 * {@code System.out} calls.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NAME = "Ekud";
    // Credits: https://patorjk.com/software/taag/#p=testall&f=Alpha&t=EKUD&x=none&v=4&h=4&w=80&we=false
    private static final String BANNER = """
            ███████╗██╗  ██╗██╗   ██╗██████╗\s
            ██╔════╝██║ ██╔╝██║   ██║██╔══██╗
            █████╗  █████╔╝ ██║   ██║██║  ██║
            ██╔══╝  ██╔═██╗ ██║   ██║██║  ██║
            ███████╗██║  ██╗╚██████╔╝██████╔╝
            ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝\s
            """;

    private final Scanner scanner;

    /**
     * Creates a UI that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the banner, welcome text, an optional startup joke, and a short
     * hint about the available commands.
     */
    public void showWelcome() {
        printDivider();
        System.out.print(BANNER);
        System.out.println("Hello! I'm " + NAME + ".");
        printStartupJoke();
        System.out.println();
        System.out.println("Add a task with todo, deadline, or event.");
        System.out.println("Other commands: list, on <date>, find <keyword>, mark <number>, "
                + "unmark <number>, delete <number>, bye.");
        printDivider();
    }

    /**
     * Prints a prompt so it is clear the chatbot is waiting for typed input.
     */
    public void showPrompt() {
        System.out.print("> ");
        System.out.flush();
    }

    /**
     * Reads the next line of input.
     * If the input stream has ended (for example the user pressed Ctrl+Z),
     * {@code bye} is returned so the session can end cleanly.
     *
     * @return the line typed by the user, or {@code bye} on end of input
     */
    public String readCommand() {
        if (!scanner.hasNextLine()) {
            return "bye";
        }
        return scanner.nextLine();
    }

    /**
     * Confirms that a task was added and reports the new list size.
     *
     * @param task      the task that was just added
     * @param taskCount number of tasks after the add
     */
    public void showAdded(Task task, int taskCount) {
        printDivider();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        String noun = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + noun + " in the list.");
        printDivider();
    }

    /**
     * Prints the tasks numbered from 1, or a short message if the list is empty.
     *
     * @param tasks the list to display
     */
    public void showTaskList(TaskList tasks) {
        printDivider();
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        printDivider();
    }

    /**
     * Prints deadlines and events that occur on {@code date}, or a short
     * message if none match.
     *
     * @param date    the date that was queried
     * @param matches tasks that occur on that date, already filtered
     */
    public void showTasksOn(LocalDate date, List<Task> matches) {
        printDivider();
        String formatted = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH));
        if (matches.isEmpty()) {
            System.out.println("No deadlines or events on " + formatted + ".");
        } else {
            System.out.println("Here are the deadlines/events on " + formatted + ":");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i));
            }
        }
        printDivider();
    }

    /**
     * Prints tasks whose descriptions contain {@code keyword}, or a short
     * message if none match.
     *
     * @param keyword the search text that was queried
     * @param matches tasks that contain the keyword, already filtered
     */
    public void showFound(String keyword, List<Task> matches) {
        printDivider();
        if (matches.isEmpty()) {
            System.out.println("No tasks matching \"" + keyword + "\".");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i));
            }
        }
        printDivider();
    }

    /**
     * Confirms that a task was deleted and reports the new list size.
     *
     * @param task      the task that was just removed
     * @param taskCount number of tasks after the delete
     */
    public void showDeleted(Task task, int taskCount) {
        printDivider();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        String noun = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + noun + " in the list.");
        printDivider();
    }

    /**
     * Confirms that a task was marked as done.
     *
     * @param task the task that was just marked
     */
    public void showMarked(Task task) {
        printDivider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printDivider();
    }

    /**
     * Confirms that a task was marked as not done.
     *
     * @param task the task that was just unmarked
     */
    public void showUnmarked(Task task) {
        printDivider();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printDivider();
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
    }

    /**
     * Prints a user-facing error, such as invalid command arguments.
     *
     * @param message explanation of what went wrong
     */
    public void showError(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * Closes the input scanner. Call this when the command loop ends.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Prints a dad joke from API Ninjas if one was fetched.
     * If the request fails (for example the API limit is exceeded), nothing is
     * printed so the greeting continues as usual.
     */
    private void printStartupJoke() {
        String joke = DadJokeFetcher.fetch();
        if (joke != null) {
            System.out.println(joke);
        }
    }

    /**
     * Prints a horizontal divider to separate chatbot messages.
     */
    private void printDivider() {
        System.out.println(DIVIDER);
    }
}
