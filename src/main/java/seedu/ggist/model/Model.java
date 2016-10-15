package seedu.ggist.model;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the Task Manager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given task as done. */
    void doneTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Edits the given task*/
    void editTask(int index,String type, String toEdit) throws UniqueTaskList.TaskTypeNotFoundException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show undone tasks */
    void updateFilteredTaskListToShowUndone();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show all done tasks */
    void updateFilteredListToShowAllDone();
    
    /** Updates the filter of the filtered task list to reflect changes */
    void updateFilteredListToShowChanges();

}
