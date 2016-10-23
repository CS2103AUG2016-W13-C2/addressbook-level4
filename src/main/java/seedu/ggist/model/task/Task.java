package seedu.ggist.model.task;


import java.util.Date;
import java.util.Objects;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.parser.DateTimeParser;


/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask{

    protected TaskName taskName;
    protected TaskDate startDate;
    protected TaskTime startTime;
    protected TaskDate endDate;
    protected TaskTime endTime;
    protected Priority priority;
    protected boolean done;
    
    protected Date start;
    protected Date end;

    /**
     * Every field must be present and not null.
     * 
    */     
    
    public Task(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.priority = priority;
        this.done = false;
    }
    

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }
    public void setDone() {
        done = true;
    }
    
    public void setUnDone() {
        done = false;
    }
      
    public Date constructDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        if (date == null && time == null) {
            return new DateTimeParser("1st January 2999 11:59pm").getDateTime();
        } else if (date == null && time != null) {
            return new DateTimeParser("1st January 2999 " + time.value).getDateTime();
        } else if (date != null && time == null) {
            return new DateTimeParser(date.value + " 11:59 pm").getDateTime();
        } else {
            return new DateTimeParser(date.value + " " + time.value).getDateTime();
        }
    }

    
    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
 
    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public Date getStartDateTime() {
        return start;
    }
    
    @Override
    public Date getEndDateTime() {
        return end;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName,startDate, startTime, endDate, endTime, priority);
    }


}
