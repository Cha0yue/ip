/**
 * A task that must be done by a given date/time, stored as the user typed it.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a deadline with the given description and due date/time.
     *
     * @param description text describing the deadline
     * @param by          due date/time, for example {@code Sunday} or {@code 11/10/2019 5pm}
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date/time text.
     *
     * @return the {@code /by} value
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns {@code D}, the type icon for deadlines.
     *
     * @return {@code D}
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the due date/time in {@code (by: ...)} form.
     *
     * @return the deadline suffix
     */
    @Override
    protected String extraDetails() {
        return " (by: " + by + ")";
    }
}
