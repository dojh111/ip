/**
 * The Todo class inherits from the Task class and is used to create todo objects
 */
public class Todo extends Task {

    public static final String TODO_ICON = "[T]";

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return TODO_ICON + super.toString();
    }
}
