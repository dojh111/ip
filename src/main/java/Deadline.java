/**
 * The Deadline class inherits from the Task class and is used to create deadline objects
 */
public class Deadline extends Task {
    public static final String D = "[D]";
    public static final String MESSAGE_INFO_START = " (By: ";
    public static final String MESSAGE_INFO_END = ")";
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return D + super.toString() + MESSAGE_INFO_START + by + MESSAGE_INFO_END;
    }
}
