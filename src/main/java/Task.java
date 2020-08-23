public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    //Returns tick or X symbols
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    //Set isDone to true
    public void markAsDone() {
        isDone = true;
    }
}
