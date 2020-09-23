package walter;

import walter.exceptions.WalterException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * The Parser class handles text parsing for Walter
 */
public class Parser {
    //Exception messages
    public static final String EXCEPTION_EMPTY_FIELD = "Oh no... You have to enter a task number. Please try again!";
    public static final String EXCEPTION_TIMEDEVENT_INTRO = "Oh no! ;-;\nThe ";
    public static final String EXCEPTION_TIMEDEVENT_BODY =
            " command requires both description and time information in the format of: \n";
    public static final String EXCEPTION_TIMEDEVENT_DESCRIPTION = "[description] ";
    public static final String EXCEPTION_TIMEDEVENT_TIMEINFO = " [time information]";
    public static final String EXCEPTION_EMPTY_TODO = "Oh no! The description of the todo cannot be empty ;-;";
    public static final String EXCEPTION_EMPTY_DATE = "Oh no! The please enter a date in YYYY-MM-DD format!";
    public static final String EXCEPTION_EMPTY_SEARCHTERM = "Oh no! The search term cannot be empty!";
    public static final String EXCEPTION_CANNOT_DETERMINE_COMMAND = "Oh no, I'm unable to determine the command...";

    public static final String BLANK_SPACE = "";
    public static final String WHITESPACE_IDENTIFIER = " ";
    public static final String HYPHEN_IDENTIFIER = "-";
    public static final int MINIMUM_LENGTH = 2;

    public static final int DATE_FORMAT_SIZE = 3;
    public static final int INVALID_COMMAND_SIZE = 1;
    public static final String DATE_FORMAT = "MMM d yyyy";

    /**
     * Removes the command passed into the method
     *
     * @param userInput Original string typed by user and to be modified
     * @param commandToRemove Command to be removed from string
     */
    public String removeCommandFromInput(String userInput, String commandToRemove) {
        String modifiedUserInput = userInput.replaceFirst(commandToRemove, BLANK_SPACE);
        return modifiedUserInput.trim();
    }

    /**
     * Returns a string array with the task's description and additional information
     *
     * @param identifier Identifier token to split the string with
     */
    public String[] determineTaskInformation(String userInput, String commandToRemove, String identifier) {
        String modifiedString = removeCommandFromInput(userInput, commandToRemove);
        return modifiedString.split(identifier);
    }

    /**
     * Checks for invalid command and throws WalterException
     *
     * @param splitUserInput Original user typed string split by whitespace
     */
    public void checkForValidInput(String[] splitUserInput) throws WalterException {
        if (isValidLength(splitUserInput, INVALID_COMMAND_SIZE)) {
            throw new WalterException(EXCEPTION_EMPTY_FIELD);
        }
    }

    /**
     * Checks and throws WalterException if no argument was passed in as argument
     *
     * @param field Argument to be checked
     */
    public void checkForEmptySingleField(String field, String command) throws WalterException {
        if (isBlankSpace(field)) {
            switch (command) {
            case "todo":
                throw new WalterException(EXCEPTION_EMPTY_TODO);
            case "find":
                throw new WalterException(EXCEPTION_EMPTY_SEARCHTERM);
            case "schedule":
                throw new WalterException(EXCEPTION_EMPTY_DATE);
            default:
                throw new WalterException(EXCEPTION_CANNOT_DETERMINE_COMMAND);
            }
        }
    }

    /**
     * Validates arguments for events with time descriptions
     *
     * @param informationStrings Arguments split by whitespaces
     * @param eventIdentifier Identifier to distinguish events and deadlines
     */
    public void checkForValidFieldEntered(String[] informationStrings, String command, String eventIdentifier)
            throws WalterException {
        boolean fieldsArePresent = true;

        //Check if both fields have been fulfilled
        for (String information : informationStrings) {
            if (isBlankSpace(information)) {
                fieldsArePresent = false;
                break;
            }
        }

        //Check for valid information
        if (isInvalidLength(informationStrings, MINIMUM_LENGTH) || !fieldsArePresent) {
            String exceptionMessage = generateExceptionMessage(command, eventIdentifier);
            throw new WalterException(exceptionMessage);
        }
    }

    /**
     * Returns true if length of string array is lesser than the minimum length
     *
     * @param splitStrings Array of strings whose length is to be compared with minimum length
     * @param minimumLength The target minimum length that splitStrings has to be
     */
    public boolean isInvalidLength(String[] splitStrings, int minimumLength) {
        return splitStrings.length < minimumLength;
    }

    /**
     * Generates the exception message for WalterException
     */
    public String generateExceptionMessage(String command, String eventIdentifier) {
        return EXCEPTION_TIMEDEVENT_INTRO + command +
                EXCEPTION_TIMEDEVENT_BODY +
                EXCEPTION_TIMEDEVENT_DESCRIPTION + eventIdentifier + EXCEPTION_TIMEDEVENT_TIMEINFO;
    }

    /**
     * Returns an arraylist which contains information to replace date in original string
     *
     * @param timeInformation The original field entered by user after the task identifier
     */
    public ArrayList<String> determineDateInformation(String timeInformation) {
        String[] splitTimeInformation = timeInformation.split(WHITESPACE_IDENTIFIER);
        ArrayList<String> replacementStrings = new ArrayList<>();
        Ui ui = new Ui();

        //Check if substring contains 2 '-'
        for (String stringInformation : splitTimeInformation) {
            if (stringInformation.contains(HYPHEN_IDENTIFIER)) {
                if (isValidDateFormat(stringInformation)) {
                    try {
                        replacementStrings = formatDateInformation(stringInformation);
                    } catch (DateTimeParseException e) {
                        ui.showInvalidDateFormatError();
                    }
                    break;
                }
            }
        }
        return replacementStrings;
    }

    /**
     * Formats date object into specified format and returns both original and formatted strings
     *
     * @param stringInformation Date information in string form
     */
    public ArrayList<String> formatDateInformation(String stringInformation) {
        LocalDate taskDate;
        ArrayList<String> replacementStrings = new ArrayList<>();

        replacementStrings.add(stringInformation);
        taskDate = LocalDate.parse(stringInformation);
        String formattedDate = taskDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        replacementStrings.add(formattedDate);

        return replacementStrings;
    }

    /**
     * Returns true when string contains 3 members separated by 2 hyphens
     *
     * @param stringInformation Date information in string form
     */
    public boolean isValidDateFormat(String stringInformation) {
        String[] splitDate = stringInformation.split(HYPHEN_IDENTIFIER);

        //Check for empty fields
        for (String subString : splitDate) {
            if (isBlankSpace(subString)) {
                return false;
            }
        }
        //Check for only 3 inputs
        return isValidLength(splitDate, DATE_FORMAT_SIZE);
    }

    /**
     * Returns true when date input fields have valid length
     *
     * @param splitArray Array of strings whose size is to be compared with the
     * @param targetSize Size that splitArray must be equal to
     */
    public boolean isValidLength(String[] splitArray, int targetSize) {
        return splitArray.length == targetSize;
    }

    /**
     * Returns true when field is empty or white space
     *
     * @param subString Current string in array of split dates
     */
    public boolean isBlankSpace(String subString) {
        return subString.trim().equals(BLANK_SPACE);
    }

    /**
     * Returns array of strings from splitting given string with whitespace
     *
     * @param userInput Original string input from user
     */
    public String[] divideUserCommand(String userInput) {
        return userInput.split(WHITESPACE_IDENTIFIER);
    }

    /**
     * Returns command from typed user input
     *
     * @param splitUserInput Original user typed string split by whitespace
     */
    public String determineCommand(String[] splitUserInput) {
        return splitUserInput[0];
    }
}
