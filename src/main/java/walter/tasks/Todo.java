package walter.tasks;

/**
 * The walter.tasks.Todo class inherits from the walter.tasks.Task class and is used to create todo objects
 */
public class Todo extends Task {

    public static final String TODO_ICON = "[T]";

    public Todo(String description) {
        super(description);
    }

    public String getTaskIcon() {
        return TODO_ICON;
    }

    @Override
    public String toString() {
        return TODO_ICON + super.toString();
    }
}