package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.ggist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
//@@author A0147994J
public class TaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskName";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String PRIORITY_FIELD_ID = "#priority";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }
    
    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    
    public String getPriority() {
    	return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getTaskName().taskName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStartDate();
    }
}
