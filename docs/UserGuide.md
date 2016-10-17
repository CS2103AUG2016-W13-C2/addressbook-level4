# User Guide

* [About GGist](#about-ggist)
* [Quick Start](#quick-start)
* [Features](#features)
* [Command Cheatsheet](#command-cheatsheet)

## About GGist

Are you constantly overwhelmed by the number of things you have to do everyday or do you have a really hard time managing them? If you answer is yes, GGist is the perfect solution to your daily struggle. GGist is a one-stop user friendly desktop organiser designed to aid working professionals like you to better organize and prioritise your everyday tasks.

Unlike most of the organisers in the market, GGist can be launched with a keyboard shortcut and accepts flexible natural language commands via keyboard. This makes it convenient for working professionals like you who can type fast, spend most of the time near a computer and prefer typing commands. 

Are you ready to embrace a new way of living and have your life better organized? Let’s begin!


## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This application may not work with earlier versions of Java 8.
   
1. Download the latest `GGist.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your GGist application.
3. Double-click on the .jar file. Press both "control" and "G" at the same time to start the app. The GUI should appear in a few seconds. 
   > <img src="images/GGistUiProto.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`**` 31 oct` : 
     lists all the tasks on 31 oct in GGist.
   * **`add`**` water the plants, jul 10, 1400` : 
     adds a task `water the plants` with deadline 2pm on the 10th of July to GGist.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to GGist<br>
General format: `add TASK,[DATE] [TIME], [PRIORITY], [FREQUENCY]`

> * `[DATE]` and `[TIME]` has to separated by a space.

>**`Date` Format**
> * `10 Oct` , `tuesday`, `tomorrow` , `next fri` works!

>**`PRIORITY` Format**
> * `low` , `med` , `high`

>**`FREQUENCY` Format**
> * `daily` , `weekly` , `fortnightly` , `monthly` , `yearly`


##### Without any deadline
Format: `add TASK, [DATE], [TIME], [PRIORITY], [FREQUENCY]`

Examples: 
* `add buy milk, low`
* `add buy present for brother's birthday`


##### With deadline
Format: `add TASK, DATE TIME, [PRIORITY], [FREQUENCY]`

Examples: 
* `add write diary, tomorrow 1300`
* `add prepare presentation slides by monday 2pm, high`

> * `[DATE]` and `[TIME]` has to separated by a space or an 'at'.<br>
> * The comma after `[TASK]` can be replaced with a `by` or 'on'.



##### With start and end time within the same day
Format: `add TASK, DATE, START TIME, END TIME, [PRIORITY], [FREQUENCY]`

Examples: 
* `add tennis, 21 nov, 1pm, 3pm, high`
* `add lunch with parents on friday from 12pm to 1pm`

> * The comma after `[TASK]` can be replaced with a `on`. <br>
> * The comma after `[DATE]` can be replaced with a `from`. <br>
> * The comma after `[START TIME]` can be replaced with a `to`. <br>


##### With start and end time on different days
Format: `add TASK, START DATE TIME, END DATE TIME, [PRIORITY], [FREQUENCY]`

Examples: 
* `add dad's birthday celebration, next thurs 1900, next thurs 2100, high`
* `add going overseas from fri at 1pm to next fri at 10pm`

> * `[DATE]` and `[TIME]` has to separated by a space. <br>
> * The comma after `[TASK]` can be replaced with a `from`. <br>
> * The comma after `[START DATE TIME]` can be replaced with a `to`.


##### Recurring
To make tasks repeating, simply  add the FREQUENCY parameter at the back.

Format: `add TASK, [DATE], [TIME], [PRIORITY], [FREQUENCY]`

Examples: 
* `add water the plants, 0800, high, daily`
* `add facial appointment, jul 10, med, monthly`

#### Listing all tasks : `list`
Shows a list of all tasks in a particular day.<br>
Format: `list [PARAMETERS]`

>**Available Listing**
> * `list` shows all incomplete tasks
> * `list all` shows all tasks
> * `list done` shows all completed tasks

#### Searching tasks by keywords: `search`
Searches and lists all tasks that contain the specified keyword, inclusive of `DAY`, `DATE`, `TIME`, `PRIORITY` and `FREQUENCY`.<br>
Format: `search KEYWORD`<br>

> * The search is not case sensitive.
> * The order of the keywords does not matter. e.g. `buy milk oct 21` will match `oct 21 buy milk`
> * Only full words will be matched e.g. `buy milk` will not match `buy cow`
> * Tasks matching keywords will be returned

Examples: 
* `search oct 1`<br>
  Lists all tasks that have the date `oct 1`.
* `search milk`<br>
  Lists the all the tasks with names containing the word `milk` if any can be found.
  
#### Deleting a task : `delete`
Deletes the specified task from the list. Irreversible.<br>
Format: `delete INDEX...`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `delete 2`<br>
  Deletes the task indexed number second in the current seen list.
* `search buy milk`<br> 
  `delete 1`<br>
  Deletes the task indexed first in the results of the `search` command.

> To delete more than one task, simply add in the other indexes separated with a comma. <br>

Example:<br>
* `delete 1, 2, 3`
* `search oct 1`
  `done 2, 3`
  
  
#### Undo : `undo`
Reverts the most recent action.<br>
Format: `undo`

#### Editing a task : `edit`
Edits task on the display list.<br>
Format: `edit INDEX PARAMETER NEW_INFORMATION`

> More than 1 parameter can be edited at the same input, simply separate them with a comma.

Examples: 
* `edit 1 date oct 11, time 1800-2000`
* `list dec 30`<br>
  `edit 5 task buy coconut, time 1500`
  
To make one of the parameters (except the task name) empty, follow the format below:<br>
Format: `edit INDEX PARAMETER clear`

Example: 
* `edit 3 time clear`

#### Marking a task as complete : `done`
Marks task on display list as complete. Does not remove completely from GGist.<br>
Format: `done INDEX`

Example:<br>
* `done 1`
* `search oct 1`<br>
  `done 2`
 
To mark more than one task as complete, simply type in the other indexes separated by a comma. <br>
Format: `done INDEX, ...`

Example:<br>
* `done 1, 2, 3`

> To view all completed tasks, simply type  `list done`.


#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
All GGist data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.
       
## Command Cheatsheet

Command | Format  
--------| -------- 
Add     | `add TASK, DATE, TIME, PRIORITY, FREQUENCY`
List    | `list DATE`
Search  | `search KEYWORD`
Delete  | `delete INDEX`
Undo    | `undo`
Edit    | `edit INDEX FIELD NEW_INFORMATION`
Done    | `done INDEX`
Help    | `help`
Exit    | `exit`
