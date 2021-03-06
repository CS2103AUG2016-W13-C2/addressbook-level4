//@@author A0147994J
package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.ReadOnlyTask;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.ggist.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;
import static seedu.ggist.logic.commands.ContinueCommand.MESSAGE_CONTINUE_TASK_SUCCESS;;

public class ContinueCommandTest extends TaskManagerGuiTest {

   
    @Test
    public void undone() throws IllegalArgumentException, IllegalValueException {
        
        //marks the first task in the list as done
        int targetIndex = 1;
        assertDoneSuccess(targetIndex);
        commandBox.runCommand("list done");
        assertContinueSuccess(targetIndex);

        //marks the last task in the list as done
        commandBox.runCommand("list");
        targetIndex = taskListPanel.getNumberOfTasks();
        assertDoneSuccess(targetIndex);
        commandBox.runCommand("list done");
        assertContinueSuccess(targetIndex);

        //marks a task from the middle of the list as done
        commandBox.runCommand("list");
        targetIndex = taskListPanel.getNumberOfTasks()/2 == 0? 1 : taskListPanel.getNumberOfTasks()/2;
        assertDoneSuccess(targetIndex);
        commandBox.runCommand("list done");
        assertContinueSuccess(targetIndex);
        
        //marks multiple task as done
        commandBox.runCommand("list");
        commandBox.runCommand("add test 1");
        commandBox.runCommand("add test 2");
        commandBox.runCommand("done 1,2");
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, "1, 2"));
        commandBox.runCommand("list done");
        commandBox.runCommand("continue 1,2");
        assertResultMessage(String.format(MESSAGE_CONTINUE_TASK_SUCCESS, "1, 2"));
        
        

        //invalid index
        commandBox.runCommand("continue " + taskListPanel.getNumberOfTasks() + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }
    /**
     * Runs the done command to mark the task at specified index as done and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark done the first task in the list, 1 should be given as the target index.
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    private void assertContinueSuccess(int targetIndexOneIndexed) throws IllegalArgumentException, IllegalValueException {
    	System.out.println(targetIndexOneIndexed);
        ReadOnlyTask taskToContinue = taskListPanel.getTask(0); 

        commandBox.runCommand("continue " + 1);
        
        //confirm the list now contains one lesser task
        assertListSize(0);
        //confirms the task mark done is no longer on the listing view
        assertEquals(taskListPanel.getTaskIndex(taskToContinue), -1);
        //confirm the task is marked done
        assertFalse(taskToContinue.isDone());

        //confirm the result message is correct

        assertResultMessage(String.format(MESSAGE_CONTINUE_TASK_SUCCESS, 1));
    }
    
    /**
     * Runs the done command to mark the task at specified index as done and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark done the first task in the list, 1 should be given as the target index.
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    private void assertDoneSuccess(int targetIndexOneIndexed) throws IllegalArgumentException, IllegalValueException {

        ReadOnlyTask taskToDone = taskListPanel.getTask(targetIndexOneIndexed-1); //-1 because array uses zero indexing

        int number = taskListPanel.getNumberOfTasks();

        commandBox.runCommand("done " + targetIndexOneIndexed);
        
        //confirm the list now contains one lesser task

        //confirms the task mark done is no longer on the listing view
        assertEquals(taskListPanel.getTaskIndex(taskToDone), -1);
        //confirm the task is marked done
        assertTrue(taskToDone.isDone());

        //confirm the result message is correct

        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, targetIndexOneIndexed));
    }


}