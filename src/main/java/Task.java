/**
 * The Task class provides a template for the Task object where description and status is stored
 */
public class Task {

    public static final String TICK_ICON = "\u2713";
    public static final String CROSS_ICON = "\u2718";

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns tick or X icons
     */
    public String getStatusIcon() {
        return (isDone ? TICK_ICON : CROSS_ICON);
    }

    /**
     * Sets isDone to true
     */
    public void setAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
