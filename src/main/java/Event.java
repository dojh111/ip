/**
 * The Event class inherits from the Task class and is used to create event objects
 */
public class Event extends Task {
    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (At: " + at + ")";
    }
}
