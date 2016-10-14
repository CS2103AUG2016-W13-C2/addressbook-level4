package seedu.ggist.model.task;

import java.util.stream.IntStream;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskName {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Tasks should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public StringProperty taskName = new SimpleStringProperty();
    public BooleanProperty filtered = new SimpleBooleanProperty();
    
    public BooleanProperty filteredProperty() {
        return this.filtered;
    }
    
    public final boolean isFiltered() {
        return this.filteredProperty().get();
    }
    
    public void setFiltered(boolean filtered) {
        this.filteredProperty().set(filtered);
    }

    /**
     * Validates given task name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskName(String taskName) throws IllegalValueException {
        assert taskName != null;
        taskName = taskName.trim();
        if (!isValidName(taskName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        editTaskName(taskName);
    }
    
    public String getTaskName() {
        return this.taskNameProperty().get();
    }
    
    public StringProperty taskNameProperty() {
        return this.taskName;
    }
    
    public void editTaskName(String newTaskName) throws IllegalValueException {
        assert newTaskName != null;
        newTaskName = newTaskName.trim();
        if (!isValidName(newTaskName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.taskNameProperty().set(newTaskName);
    }
    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }
    

    @Override
    public String toString() {
        return taskName.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && this.taskName.equals(((TaskName) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
