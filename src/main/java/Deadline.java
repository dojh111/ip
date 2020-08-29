/**
 * The Deadline class inherits from the Task class and is used to create deadline objects
 */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + by + ")";
    }
}
