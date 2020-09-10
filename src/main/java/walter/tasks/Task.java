package walter.tasks;

/**
 * The walter.tasks.Task class provides a template for the walter.tasks.Task object where description and status is stored
 */
public abstract class Task {

    public static final String TICK_ICON = "\u2713";
    public static final String CROSS_ICON = "\u2718";

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? TICK_ICON : CROSS_ICON);
    }

    public void setAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getTaskIcon();

    public abstract String getTimingInformation();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
