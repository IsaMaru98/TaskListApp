package com.isamaru.tasklist.mvp;

import com.isamaru.tasklist.view.dto.TaskItem;

import java.util.List;

public interface TaskMVP {

    interface ModelTask {

        List<TaskItem> getTasks();

        void saveTask(TaskItem task);

        void updateTask(TaskItem item);
    }
    interface PresenterTask{
        void loadTasks();
        void addNewTask();


        void taskItemClicked(TaskItem item);
    }
    interface  ViewTask{

        void showTaskList(List<TaskItem> items);
        String getTaskDescription();

        void addTaskToList(TaskItem task);

        void updateTask(TaskItem task);
    }
}
