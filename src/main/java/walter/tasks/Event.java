package walter.tasks;

/**
 * The walter.tasks.Event class inherits from the walter.tasks.Task class and is used to create event objects
 */
public class Event extends Task {

    public static final String EVENT_ICON = "[E]";
    public static final String MESSAGE_INFO_START = " (At: ";
    public static final String MESSAGE_INFO_END = ")";

    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return EVENT_ICON + super.toString() + MESSAGE_INFO_START + at + MESSAGE_INFO_END;
    }
}
