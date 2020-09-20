package walter.tasks;

import java.time.LocalDate;

/**
 * The Event class inherits from the Task class and is used to create event objects
 */
public class Event extends Task {

    public static final String EVENT_ICON = "[E]";
    public static final String MESSAGE_INFO_START = " (At: ";
    public static final String MESSAGE_INFO_END = ")";

    protected String at;
    protected LocalDate date;

    public Event(String description, String at, String date) {
        super(description);
        this.at = at;
        this.date = LocalDate.parse(date);
    }

    public String getTaskIcon() {
        return EVENT_ICON;
    }

    public String getTimingInformation() {
        return at;
    }

    public String getDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return EVENT_ICON + super.toString() + MESSAGE_INFO_START + at + MESSAGE_INFO_END;
    }
}
