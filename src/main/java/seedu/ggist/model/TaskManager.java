package seedu.ggist.model;

import javafx.collections.ObservableList;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.EditCommand;
import seedu.ggist.model.task.TaskDate;
import seedu.ggist.model.task.TaskTime;
import seedu.ggist.model.task.TaskName;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.EventTask;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public TaskManager() {
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public TaskManager(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }


    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(t -> {
            try {
                return new EventTask(t);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList());
    }

    //// task-level operations

    /**
     * Adds a task to the task manager. 
     
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(t);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
  //@@author A0144727B
    public void continueTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.contains(key)) {
            key.setContinue();
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
  //@@author
    
  //@@author A0147994J
    public void doneTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.contains(key)) {
            key.setDone();
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
   //@@author
    
   //@@author A0144727B
    public void editTask(ReadOnlyTask key, String field, String value) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        if (tasks.contains(key)) {
            tasks.edit(key, field, value);
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
  //@@author

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }


    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.tasks.equals(((TaskManager) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks);
    }

}
