package walter.tasks;

/**
 * The Task class provides a template for the Task object where description and status is stored.
 */
public abstract class Task {

    public static final String TICK_ICON = "Y";
    public static final String CROSS_ICON = "X";

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? TICK_ICON : CROSS_ICON);
    }

    public boolean getStatus() {
        return isDone;
    }

    public void setAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getTaskIcon();

    public abstract String getTimingInformation();

    public abstract String getDate();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
