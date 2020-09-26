# User Guide

Walter is a **desktop app for managing daily tasks, optimized for use via a Command Line Interface** (CLI).
For fast typists, Walter will allow you to add and keep track of your daily tasks faster than traditional 
calendar GUI apps.

* [Quick Start](#quick-start)
* [Features](#features)
  * [Viewing help: `help`](#viewing-help-help)
  * [Adding a todo task: `todo`](#adding-a-todo-task-todo)
  * [Adding an event task: `event`](#adding-an-event-task-event)
  * [Adding a deadline task: `deadline`](#adding-a-deadline-task-deadline)
  * [Viewing all tasks: `list`](#viewing-all-tasks-list)
  * [Setting a task as done: `done`](#setting-a-task-as-done-done)
  * [Looking for a task: `find`](#looking-for-a-task-find)
  * [Looking for all tasks on a date: `schedule`](#looking-for-all-tasks-on-a-date-schedule)
  * [Deleting a task: `delete`](#deleting-a-task-delete)
  * [Clearing all tasks: `clear`](#clearing-all-tasks-clear)
  * [Exiting the program: `bye`](#exiting-the-program-bye)
  * [Saving the data](#saving-the-data)
* [FAQ](#faq)
* [Command Summary](#command-summary)

---
  
## Quick Start
1. Ensure you have Java `11` installed in your Computer.
1. Download the latest `walter.jar` from [here]().
1. Copy the jar file to a *suitable location* on your computer.\
*(Note: On the first launch of `walter.jar`, a save file will be created in the same directory
as the jar file)*
1. Open a new **terminal** window and navigate to the same directory where your `walter.jar` is located.
1. Enter the **following command** into the **terminal** window to launch the application:
````
java -jar walter.jar
````
You will be greeted with a welcome screen from Walter on successful launch.
*Notice how a new save file is created if no previous save file exists in the same directory as `walter.jar`.*\
\
![Start up screen image](https://github.com/dojh111/ip/blob/master/docs/images/StartupScreen.png?raw=true)
1. You are now ready to use *Walter*. Type commands into the **terminal** window and press enter to get *Walter* 
to execute commands. e.g. typing `help` and pressing enter will bring up all available commands.\
Here are some example commands you can try:
    * `help`: Brings up all available commands.
    * `todo Buy some bread`: Adds a todo task with description `Buy some bread` to the task list.
    * `event concert /at Stadium 2021-01-09`: Adds an event with description `concert (At: Stadium Jan 9 2021)` 
    to the task list.
    * `list`: Lists all available tasks.
    * `delete 1`: Deletes 1st task shown in list.
    * `bye`: Exits the program.
1. Refer to the [Features](#features) section below for further details of each command.

---

## Features 
**Notes about the command format:**
* **Words in `UPPER_CASE` are the parameters to be supplied by the user.**\
e.g in `todo TASK_DESCRIPTION`, `TASK_DESCRIPTION` is a parameter which can be used as `todo Do work`.
* **Items in square brackets are optional.**\
e.g. `/by ADDITIONAL_INFORMATION [DATE]` can be used as `/by This Friday 2021-02-07` or `/by This Friday`.
* **Dates can be input in any order.**\
As long as the date is input in the correct `YYYY-MM-DD` format, and comes after the `/by` or `/at` tags.

---
### Viewing help: `help`
Displays all available commands on the terminal.\
Format: `help`

![Help command image](https://github.com/dojh111/ip/blob/master/docs/images/Help.png?raw=true)

---

### Adding a todo task: `todo`
Adds a todo task to the task list without any additional information.\
Format: `todo TASK_DESCRIPTION`

**Examples:**
* `todo Call up a friend` Adds a todo task `Call up a friend` to the task list.
* `todo Watch videos` Adds another todo task `Watch videos` to the task list.

---

### Adding an event task: `event`
Adds an event task to the task list with additional timing information.\
Format: `event TASK_DESCRIPTION /at ADDITIONAL_INFORMATION [DATE]`

* Both the `TASK_DESCRIPTION` and `ADDITIONAL_INFORMATION` *(or optional `DATE`)* fields must be present.
* If `DATE` is provided, it must be in the format of `YYYY-MM-DD` for the field to be detected as a date. Else,
it will be saved as part of the description.

**Examples:**
* `event Team meeting /at 8pm tonight` Adds event `Team meeting (At: 8pm tognight)` to the task list.
* `event Concert /at 8-9pm, 2021-01-09` Adds another event `Concert (At: 8-9pm, Jan 9 2021)` to the task list.

![Event command image](https://github.com/dojh111/ip/blob/master/docs/images/Event.png?raw=true)

---

### Adding a deadline task: `deadline`
Adds a deadline task to the task list with additional timing information.\
Format: `deadline TASK_DESCRIPTION /by ADDITIONAL_INFORMATION [DATE]`

* Similar to [event](#adding-an-event-task-event).
* Both the `TASK_DESCRIPTION` and `ADDITIONAL_INFORMATION` *(or optional `DATE`)* fields must be present.
* If `DATE` is provided, it must be in the format of `YYYY-MM-DD` for the field to be detected as a date. Else,
it will be saved as part of the description.

**Examples:**
* `deadline Return books /by This weekend` Adds deadline `Return books (By: This weekend)` to the task list.
* `deadline Pay bills /by 2022-04-13` Adds deadline `Pay bills (By: Apr 13 2022)`.

![Deadline command image](https://github.com/dojh111/ip/blob/master/docs/images/Deadline.png?raw=true)

---

### Viewing all tasks: `list`
Displays all available tasks on the task list.\
Format: `list`

![list command image](https://github.com/dojh111/ip/blob/master/docs/images/List.png?raw=true)

---

### Setting a task as done: `done`
Sets the specified task as done.\
Format: `done INDEX`

* Sets the task as done at the specified `INDEX`.
* The index refers to the index number shown in the displayed task [list](#viewing-all-tasks-list).
* The index **must be a positive integer** e.g 1, 2, 3, ...

**Example:**
* `done 2` sets the second task from `list` as done.

![Done command image](https://github.com/dojh111/ip/blob/master/docs/images/Done.png?raw=true)

---

### Looking for a task: `find`
Finds all tasks in the task list that contain the given keywords.\
Format: `find KEYWORD [MORE_KEYWORDS]`

* The search **is** case-sensitive: `books` will **not** match `Books`
* The search will follow the order of the keywords: `Buy Bread` will **not** match `Bread Buy`
* Only tasks containing all keywords in the correct order will be returned.
* Only full words will be matched: `Song` will not match `Songs`
* Only the description is searched.

**Examples:**
* `find buy bread` returns `buy bread (By: Next morning)` and `Go and buy bread`

![Find command image](https://github.com/dojh111/ip/blob/master/docs/images/Find.png?raw=true)

---

### Looking for all tasks on a date: `schedule`
Finds all tasks in the task list that fall on the same date as the given date.\
Format: `schedule DATE`

* The given date must be in the format of `YYYY-MM-DD`.
* Only tasks created with a valid date will be returned: Tasks with the dates found only in their description
will not be returned.
* Only the dates saved and linked to the task will be searched.

**Examples:**
* `schedule 2021-09-01` returns `Concert (At: 8-9pm, Jan 9 2021)` and `Buy gifts (By: Jan 9 2021)`

![Schedule command image](https://github.com/dojh111/ip/blob/master/docs/images/Schedule.png?raw=true)

---

### Deleting a task: `delete`
Deletes the specified task from the task list.\
Format: `delete INDEX`

* Deletes the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task [list](#viewing-all-tasks-list).
* The index **must be a positive integer** e.g 1, 2, 3, ...

**Examples:**
* `delete 1` deletes the first task from `list`
![Delete1 command image](https://github.com/dojh111/ip/blob/master/docs/images/Delete1.png?raw=true)
* `delete 3` deletes the third task from `list`
![Delete3 command image](https://github.com/dojh111/ip/blob/master/docs/images/Delete3.png?raw=true)

---

### Clearing all tasks: `clear`
Clears all entries from the task list.\
Format: `clear`

---

### Exiting the program: `bye`
Exits the Walter program.\
Format: `bye`

---

### Saving the data
All Walter data is saved onto the hard disk automatically after any command that changes the data. Hence, there is
no need to manually save the data. When no save file is detected on startup, the program will automatically create 
a new save file.

---

## FAQ
**Q:** Can I move my save data to another location/Computer?\
**A:** Yes you can. After downloading `walter.jar` onto the other device, you can either:
1. Place a copy of the current `walter.txt` save file in the same directory of `walter.jar` being launching 
the application
1. Replace the save file `walter.txt` that was created by the program and found in the same directory as `walter.jar`
with your own save file.

**Q:** Why does the `schedule` command not return a task that falls on the same date as the given date?\
**A:** Please ensure when the original task was created, the date was input in the format of `YYYY-MM-DD`, after 
the `/at` identifier for `event` command and `/by` for `deadline` command. When successful, the input date in the 
`YYYY-MM-DD` format will be automatically reformatted into a `MMM-DD-YYYY` format.\
*e.g `2022-03-04` will be reformatted into `Mar 04 2022` in the task description.*

---

## Command Summary

Command | Format, Examples
--------|-----------------
**Help**|`help`
**Todo**|`todo TASK_DESCRIPTION` e.g,`todo Watch videos`
**Event**|`event TASK_DESCRIPTION /at ADDITIONAL_INFORMATION [DATE]` e.g,`event Team meeting /at 8pm tonight`
**Deadline**|`deadline TASK_DESCRIPTION /by ADDITIONAL_INFORMATION [DATE]` e.g,`deadline Pay bills /by 2022-04-13`
**List**|`list`
**Done**|`done INDEX` e.g, `done 2`
**Find**|`find KEYWORD [MORE_KEYWORDS]` e.g,`find bread`
**Schedule**|`schedule DATE` e.g,`schedule 2021-01-09`
**Delete**|`delete INDEX` e.g,`delete 3`
**Clear**|`clear`
**Bye**|`bye`