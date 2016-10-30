package seedu.ggist.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.ggist.commons.core.ComponentManager;
import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.events.model.TaskManagerChangedEvent;
import seedu.ggist.commons.events.ui.JumpToListRequestEvent;
import seedu.ggist.commons.events.ui.ChangeListingEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.StringUtil;
import seedu.ggist.logic.commands.Command;
import seedu.ggist.logic.commands.CommandResult;
import seedu.ggist.logic.commands.EditCommand;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.TaskDate;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;
import seedu.ggist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<Task> filteredTasks;
    private SortedList<Task> sortedTasks;
    private String today;
    
    public String lastListing;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }
  //@@author A0138411N
    /**
     * Constructor for Model Manager
     * Sets current date
     * @param initialData
     * @param userPrefs
     */
    @SuppressWarnings("unchecked")
    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        sortedTasks = new SortedList<>(filteredTasks, Task.getTaskComparator());
        setTodayDate();
        updateListing();
        raise(new ChangeListingEvent(lastListing));
    }
    
    @Override
    public String getLastListing() {
        return lastListing;
    }
    
    @Override
    public void setLastListing(String listing) {
        lastListing = listing;
    }
  //@@author   
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    //@@author A0138411N
    /**Create a Date object with today's date*/
    private void setTodayDate() {
        today = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM YY"));
        lastListing = today;
    }
    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }
    
    /** Raises an event to indicate the new task added */
    private void indicateTaskChanges(Task task) {
        indicateTaskManagerChanged();
        raise(new JumpToListRequestEvent(getFilteredTaskList().indexOf(task)));
    }
//@@author 
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }
  //@@author A0144727B
    @Override
    public synchronized void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.doneTask(target);
        updateListing();
        indicateTaskManagerChanged();
    }

    public synchronized void editTask(Task task, String field, String value) throws TaskNotFoundException, IllegalValueException {
        taskManager.editTask(task, field, value);
        updateListing();
        indicateTaskChanges(task);
    }
  //@@author
    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateListing();
        indicateTaskChanges(task);

    }

    //=========== Filtered Task List Accessors ===============================================================
  //@@author A0138411N
    /**
     * Updates filtered list to show based on last shown listing choice
     */
    public void updateListing() {
        if (lastListing == null) {
            updateFilteredListToShowDate(today);
        } else if (lastListing.equals("")) {
            updateFilteredListToShowAllUndone();
        } else if (lastListing.equals("done")) {
            updateFilteredListToShowAllDone();
        } else if (lastListing.equals("all")){
            updateFilteredListToShowAll();
        } else if (lastListing.equals("high") || lastListing.equals("med") || lastListing.equals("low")) {
            updateFilteredListToShowPriority(lastListing);
        } else if (TaskDate.isValidDateFormat(lastListing)) {
            updateFilteredListToShowDate(lastListing);
        }
    }
     
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return getSortedTaskList();
    }
    
    @Override
    /**
     * Sorts filtered list based on start date and time
     */
    public UnmodifiableObservableList<ReadOnlyTask> getSortedTaskList() {
        sortedTasks = new SortedList<>(filteredTasks, Task.getTaskComparator());
        return new UnmodifiableObservableList<>(sortedTasks);
    }
  //@@author A0144727B
    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredListToShowAll(new PredicateExpression(new AllQualifier()));
    }
    public void updateFilteredListToShowAll(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowAllDone() {
        updateFilteredListToShowAllDone(new PredicateExpression(new DoneQualifier()));
    }
    
    private void updateFilteredListToShowAllDone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowAllUndone() {
        updateFilteredListToShowAllUndone(new PredicateExpression(new NotDoneQualifier()));
    }
    
    private void updateFilteredListToShowAllUndone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
  //@@author A0138411N
    @Override
    public void updateFilteredListToShowDate(String keywords){
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(keywords)));
    }

    private void updateFilteredListToShowDate(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowPriority(String keyword){
        updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(keyword)));
    }

    private void updateFilteredListToShowPriority(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
  //@@a
  //@@author A0144727B
    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    public void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowChanges() {
        System.out.println(filteredTasks.getPredicate());
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }
    
    private class AllQualifier implements Qualifier {
        AllQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return true;
        }
    }
    
    private class NotDoneQualifier implements Qualifier {
        
        NotDoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return (!task.isDone());
        }
    }
    
    private class DoneQualifier implements Qualifier {
        
        DoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return task.isDone();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> taskNameKeyWords;

        NameQualifier(Set<String> taskNameKeyWords) {
            this.taskNameKeyWords = taskNameKeyWords;
        }

        @Override

        public boolean run(ReadOnlyTask task) {
            return taskNameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskName().taskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", taskNameKeyWords);
        }
    }
  //@@author A0138411N
    private class DateQualifier implements Qualifier {
        private String taskDateKeyWords;

        DateQualifier(String taskDateKeyWords) {
            this.taskDateKeyWords = taskDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (taskDateKeyWords == null) {
                return true;
            }
            return ((taskDateKeyWords.equalsIgnoreCase(task.getStartDate().toString()) || 
                   taskDateKeyWords.equalsIgnoreCase(task.getEndDate().toString())) && !task.isDone()) ||
                   ((task.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) && 
                   (task.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET) &&
                   (task.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) && 
                   (task.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)) && !task.isDone())) ||
                   (task.isOverdue() && !task.isDone())));
        }
        
        @Override
        public String toString() {
            return "name=" + String.join(", ", taskDateKeyWords);
        }
  
    }
        
        private class PriorityQualifier implements Qualifier {
            private String priority;

            PriorityQualifier(String priority) {
                this.priority = priority;
            }
            @Override
            public boolean run(ReadOnlyTask task) {
                return (priority.equals(task.getPriority().toString()) && !task.isDone());                      
            }
        }
        
      //@@author

}
