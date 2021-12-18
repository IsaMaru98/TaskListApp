package com.isamaru.tasklist.model;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.isamaru.tasklist.mvp.TaskMVP;
import com.isamaru.tasklist.view.TasksActivity;
import com.isamaru.tasklist.view.dto.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class TaskInteractor implements TaskMVP.ModelTask {

    private final List<TaskItem> tempItems;

    public TaskInteractor() {
        tempItems = new ArrayList<>();
        tempItems.add(new TaskItem("Mercar", "December 16th,2021"));
    }
    @Override
    public List<TaskItem> getTasks() { //Aquí se haría la conxión a la BD para obtener la lista, pero lo vamos a hacer hardcoded

        return new ArrayList<>(tempItems);
    }

    @Override
    public void saveTask(TaskItem task) {
        tempItems.add(task);


    }

    @Override
    public void updateTask(TaskItem item) {

    }

    @Override
    public void deleteTask(TaskItem task) {

    }

    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
