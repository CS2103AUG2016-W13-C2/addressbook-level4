package seedu.ggist.logic.commands;

import seedu.ggist.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "GGist has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
