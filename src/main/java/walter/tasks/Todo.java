package walter.tasks;

import java.time.LocalDate;

/**
 * The Todo class inherits from the Task class and is used to create todo objects
 */
public class Todo extends Task {

    public static final String TODO_ICON = "[T]";
    public static final String TIME_INFO = "";
    public static final String DEFAULT_DATE = "9999-12-31";

    private LocalDate date;

    public Todo(String description) {
        super(description);
        this.date = LocalDate.parse(DEFAULT_DATE);
    }

    public String getTaskIcon() {
        return TODO_ICON;
    }

    public String getTimingInformation() {
        return TIME_INFO;
    }

    public String getDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return TODO_ICON + super.toString();
    }
}
