/**
 * A task that starts and ends at given date/times, stored as the user typed them.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event with the given description and start/end date/times.
     *
     * @param description text describing the event
     * @param from        start date/time, for example {@code Mon 2pm}
     * @param to          end date/time, for example {@code 4pm}
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date/time text.
     *
     * @return the {@code /from} value
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end date/time text.
     *
     * @return the {@code /to} value
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns {@code E}, the type icon for events.
     *
     * @return {@code E}
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Returns the start and end date/times in {@code (from: ... to: ...)} form.
     *
     * @return the event suffix
     */
    @Override
    protected String extraDetails() {
        return " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns {@code event DESCRIPTION /from START /to END}.
     *
     * @return the add-command text for this event
     */
    @Override
    public String toCommandString() {
        return CommandType.EVENT.getKeyword() + " " + getDescription()
                + " /from " + from + " /to " + to;
    }
}
