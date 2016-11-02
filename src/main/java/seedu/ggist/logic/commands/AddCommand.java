package seedu.ggist.logic.commands;



import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.*;

/**
 * Adds a task to GGist.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
  //@@author A0138411N
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task\n"
            + "Parameters: TASK, [DATE] [TIME] [-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " examination period from friday 1pm to next friday 12pm -high";
    
    public static final String MESSAGE_SUCCESS = "New %1$s added: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "duplicated tasks found";
      
    private final Task toAdd;
    private Task.TaskType taskType;

    /**
     * Adds an event task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String startDate, String startTime, String endDate, String endTime, String priority) throws IllegalValueException {      
        
        if (startTime.equals("")) {
            startTime = Messages.MESSAGE_NO_START_TIME_SET;
        }
               
        if (endTime.equals("")) {
            endTime = Messages.MESSAGE_NO_END_TIME_SET;
        }
        this.toAdd = new EventTask(
                new TaskName(taskName),
                new TaskDate(startDate),
                new TaskTime(startTime),
                new TaskDate(endDate),
                new TaskTime(endTime),
                new Priority(priority)
        );
        taskType = Task.TaskType.EVENT;
    }
    
    /**
     * Adds a deadline task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String date, String endTime, String priority) throws IllegalValueException {      
        
        if (endTime.equals("")) {
            endTime = Messages.MESSAGE_NO_END_TIME_SET;
        }
        
        this.toAdd = new DeadlineTask(
                new TaskName(taskName),
                new TaskDate(date),
                new TaskTime(endTime),
                new Priority(priority)
        );
        taskType = Task.TaskType.DEADLINE;
    }
    
    /**
     * Adds a floating task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String priority) throws IllegalValueException {      

        this.toAdd = new FloatingTask(
                new TaskName(taskName),
                new Priority(priority)
        );
        taskType = Task.TaskType.FLOATING;
    }
    
    public String getTaskType() {
        return taskType.toString();
    }
  //@@author A0138420N
    @Override
    public CommandResult execute() {
       
        assert model != null;
        try {
            model.addTask(toAdd); 
            model.getListOfCommands().push(COMMAND_WORD);
            model.getListOfTasks().push(toAdd);
            indicateCorrectCommandExecuted();
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskType, toAdd.getTaskName()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
  //@@author
    @Override
    public  String toString(){
        return COMMAND_WORD;
    }

}
