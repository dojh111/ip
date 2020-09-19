package walter;

import walter.exceptions.WalterException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    //Exception messages
    public static final String EXCEPTION_EMPTY_FIELD = "Oh no... You have to enter a task number. Please try again!";
    public static final String EXCEPTION_TIMEDEVENT_INTRO = "Oh no! ;-;\nThe ";
    public static final String EXCEPTION_TIMEDEVENT_BODY =
            " command requires both description and time information in the format of: \n";
    public static final String EXCEPTION_TIMEDEVENT_DESCRIPTION = "[description] ";
    public static final String EXCEPTION_TIMEDEVENT_TIMEINFO = " [time information]";
    public static final String EXCEPTION_INVALID_DATE_FORMAT = "Invalid date format entered: \n [YYYY-MM-DD]";

    public static final String BLANK_SPACE = "";
    public static final String WHITESPACE_IDENTIFIER = " ";
    public static final String HYPHEN_IDENTIFIER = "-";
    public static final int DATE_FORMAT_SIZE = 3;

    /**
     * Removes the command passed into the method and replaces it with white space
     *
     * @params userInput  Original string typed by user and to be modified
     * @params commandToRemove  Command to be removed from string
     */
    public static String removeCommandFromInput(String userInput, String commandToRemove) {
        String modifiedUserInput = userInput.replace(commandToRemove, BLANK_SPACE);
        return modifiedUserInput.trim();
    }

    /**
     * Returns a string array with the task's description and additional information
     *
     * @params userInput Original string typed by user and to be modified
     * @params commandToRemove  Command to be removed from string
     * @params identifier  Identifier token to split the string with
     **/
    public static String[] determineTaskInformation(String userInput, String commandToRemove, String identifier) {
        String modifiedString = removeCommandFromInput(userInput, commandToRemove);
        return modifiedString.split(identifier);
    }

    /** Checks for invalid command and throws WalterException */
    public static void checkForValidInput(String[] splitUserInput) throws WalterException {
        if (splitUserInput.length == 1) {
            throw new WalterException(EXCEPTION_EMPTY_FIELD);
        }
    }

    public static void checkForValidFieldEntered(String[] informationStrings, String command, String eventIdentifier)
            throws WalterException {
        boolean fieldsArePresent = true;

        //Check if both fields have been fulfilled
        for (String information : informationStrings) {
            if (information.equals(BLANK_SPACE)) {
                fieldsArePresent = false;
                break;
            }
        }

        //Check for valid information
        if (informationStrings.length < 2 || !fieldsArePresent) {
            String exceptionMessage = EXCEPTION_TIMEDEVENT_INTRO + command +
                    EXCEPTION_TIMEDEVENT_BODY +
                    EXCEPTION_TIMEDEVENT_DESCRIPTION + eventIdentifier + EXCEPTION_TIMEDEVENT_TIMEINFO;
            throw new WalterException(exceptionMessage);
        }
    }

    public static LocalDate determineDateInformation(String timeInformation) {
        String[] splitTimeInformation = timeInformation.split(WHITESPACE_IDENTIFIER);
        LocalDate taskDate = LocalDate.parse("9999-12-01");

        //Check if substring contains 2 '-' to try to parse into date information
        for (String stringInformation : splitTimeInformation) {
            if (stringInformation.contains(HYPHEN_IDENTIFIER)) {
                if (checkForValidDateFormat(stringInformation)) {
                    try {
                        taskDate = LocalDate.parse(stringInformation);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println(EXCEPTION_INVALID_DATE_FORMAT);
                    }
                }
            }
        }
        return taskDate;
    }

    public static boolean checkForValidDateFormat(String stringInformation) {
        String[] splitDate = stringInformation.split(HYPHEN_IDENTIFIER);

        //Check for empty fields
        for (String subString : splitDate) {
            if (subString.equals(BLANK_SPACE)) {
                return false;
            }
        }
        //Check for only 3 inputs
        if (splitDate.length == DATE_FORMAT_SIZE) {
            return true;
        }
        return false;
    }

    /** Splits string by white space and returns array of strings */
    public String[] divideUserCommand(String userInput) {
        return userInput.split(WHITESPACE_IDENTIFIER);
    }

    /** Determines command from string */
    public String determineCommand(String[] splitUserInput) {
        return splitUserInput[0];
    }
}
