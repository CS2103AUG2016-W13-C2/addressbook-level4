package seedu.ggist.model.task;


import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in GGist 
 * Guarantees: immutable; is valid as declared in {@Time #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task time should be a number with 4 digits (hours and minutes)";
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";
    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns if a given string is a valid time format.
     */
    public static boolean isValidTime(String test) {
    	return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instance of handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
