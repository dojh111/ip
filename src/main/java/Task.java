/**
 * The Task class provides a template for the Task object where description and status is stored
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns tick or X icons */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    /** Sets isDone to true */
    public void setAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
