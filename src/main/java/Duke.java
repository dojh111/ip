import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    //Prints separator component after text is printed
    public static void printSeparator() {
        System.out.println("______________________________________________________________________________");
    }

    //Handles commands entered by the user
    public static void handleNewTask(Task[] tasks, String description, int taskCount) {
        Task newTask = new Task(description);
        tasks[taskCount] = newTask;
        printSeparator();
        System.out.println(" Added task: " + description);
        printSeparator();
    }

    //Print list of tasks when user requests
    public static void printTaskList(Task[] tasks) {
        int taskNumber = 1;
        printSeparator();
        if(tasks.length == 0) {
            System.out.println("No tasks available... (｡◕‿‿◕｡)");
        }
        for(Task task : tasks) {
            System.out.println(" " + taskNumber + ":[" + task.getStatusIcon() + "] " + task.description);
            taskNumber++;
        }
        printSeparator();
    }

    //Set isDone of Task object to true
    public static void markTaskAsDone(Task[] tasks, String description, int taskCount) {
        //Determine index of task to be marked as done
        String[] splitDescription = description.split(" ");
        if(splitDescription.length == 1) {
            System.out.println("Invalid command entered... (ㆆ _ ㆆ)");
            printSeparator();
            return;
        }
        int taskNumber = Integer.parseInt(splitDescription[1]) - 1;
        printSeparator();
        //Check if taskNumber is out of bounds
        if(taskNumber < 0 || taskNumber > taskCount - 1) {
            System.out.println("Invalid task number entered... (ㆆ _ ㆆ)");
            printSeparator();
            return;
        }
        //TaskNumber is valid
        tasks[taskNumber].markAsDone();
        System.out.println("NICE! (｡◕‿‿◕｡) I've marked the task as done!:");
        System.out.println("  [" + "\u2713" + "] " + tasks[taskNumber].description);
        printSeparator();
    }

    public static void main(String[] args) {
        String logo = "____    __    ____  ___       __      .___________. _______ .______      \n"
                + "\\   \\  /  \\  /   / /   \\     |  |     |           ||   ____||   _  \\     \n"
                + " \\   \\/    \\/   / /  ^  \\    |  |     `---|  |----`|  |__   |  |_)  |    \n"
                + "  \\            / /  /_\\  \\   |  |         |  |     |   __|  |      /     \n"
                + "   \\    /\\    / /  _____  \\  |  `----.    |  |     |  |____ |  |\\  \\----.\n"
                + "    \\__/  \\__/ /__/     \\__\\ |_______|    |__|     |_______|| _| `._____|\n";
        String endLogo = "________              \n"
                + "___  __ )____  ______ \n"
                + "__  __  |_  / / /  _ \\\n"
                + "_  /_/ /_  /_/ //  __/\n"
                + "/_____/ _\\__, / \\___/ \n"
                + "        /____/        ";
        String userInput;
        String[] command;
        boolean isFinished = true;
        Task[] tasks = new Task[100];
        int taskCount = 0;
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        printSeparator();
        System.out.println(" Hello! I'm Walter  ◕_◕");
        System.out.println(" What can I do for you?");
        printSeparator();

        //Loop infinitely until user enters "bye"
        while(isFinished) {
            userInput = in.nextLine();
            command = userInput.split(" ");
            switch (command[0]) {
            case "bye":
                isFinished = false;
                break;
            case "list":
                printTaskList(Arrays.copyOf(tasks, taskCount));
                break;
            case "done":
                markTaskAsDone(tasks, userInput, taskCount);
                break;
            default:
                //Add new task into tasks array
                handleNewTask(tasks, userInput, taskCount);
                taskCount++;
                break;
            }
        }

        printSeparator();
        System.out.println(" I'm sad to see you go ε(´סּ︵סּ`)з. Hope to see you again soon! ／人◕ ‿‿ ◕人＼");
        System.out.println(endLogo);
        printSeparator();
    }
}
