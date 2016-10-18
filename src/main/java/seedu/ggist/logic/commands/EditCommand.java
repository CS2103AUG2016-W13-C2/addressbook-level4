package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.ggist.model.task.*;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Edits task information
 * 
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits a task identified by the index number used in the last task listing. "
            + "Parameters: INDEX [FIELD TO CHANGE] [NEW INFO] \n\t" + "Example: " + COMMAND_WORD + " 1, task, buy eggs";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";

    public int targetIndex;
    public String field;
    public String value;

    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, String field, String value) {
        this.targetIndex = targetIndex;
        this.field = field;
        this.value = value;

    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        boolean correctField = field.equals("task") || field.equals("start date") || field.equals("end date") || field.equals("start time") || field.equals("end time");
        if (!correctField) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_FIELD);
        }
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        try {
            model.editTask(taskToEdit, field, value);
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(taskToEdit);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}