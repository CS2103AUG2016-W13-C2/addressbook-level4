package seedu.ggist.commons.events.model;

import seedu.ggist.commons.events.BaseEvent;
import seedu.ggist.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of done tasks " + data.getDoneTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
