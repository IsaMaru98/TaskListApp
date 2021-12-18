package com.isamaru.tasklist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.isamaru.tasklist.LoginActivity;
import com.isamaru.tasklist.R;
import com.isamaru.tasklist.mvp.TaskMVP;
import com.isamaru.tasklist.presenter.TaskPresenter;
import com.isamaru.tasklist.view.adapter.TaskAdapter;
import com.isamaru.tasklist.view.dto.TaskItem;

import java.util.List;

public class TasksActivity extends AppCompatActivity implements TaskMVP.ViewTask {

    private TextInputLayout tilNewTask;
    private TextInputEditText etNewTask;
    private RecyclerView rvTasks;

    private TaskAdapter taskAdapter;

    private EditText description;

    //variable para crear objeto presenter y utilizar los metodos
    private TaskMVP.PresenterTask presenterTask;


    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        presenterTask = new TaskPresenter(TasksActivity.this);

        initUI();
        presenterTask.loadTasks();

        btnCerrar = (Button) findViewById(R.id.btnLogOut);
        btnCerrar.setOnClickListener(v ->
                presenterTask.logOut());//Fin boton cerrar sesión
    }//Fin onCreate

    private void initUI() {
        tilNewTask = findViewById(R.id.til_new_task);
        tilNewTask.setEndIconOnClickListener(v -> presenterTask.addNewTask());

         /* Para que aparezca un Toast diciendo que se agragó tal tarea
        tilNewTask.setEndIconOnClickListener(v -> {



            String description =  etNewTask.getText().toString();

            Toast.makeText(TasksActivity.this, "Task " + description+" Added", Toast.LENGTH_SHORT)
                    .show();
        });*/

        etNewTask = findViewById(R.id.et_new_task);

        taskAdapter = new TaskAdapter();
        taskAdapter.setClicklistener(item -> presenterTask.taskItemClicked(item));
        taskAdapter.setLongClickListener(item -> presenterTask.taskItemLongClicked(item));
        rvTasks = findViewById(R.id.rv_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(TasksActivity.this));
        rvTasks.setAdapter(taskAdapter);
    }



    @Override
    public void showTaskList(List<TaskItem> items) {
        taskAdapter.setData(items);
    }

    @Override
    public String getTaskDescription() {
        return etNewTask.getText().toString();
    }

    @Override
    public void addTaskToList(TaskItem task) {
        taskAdapter.addItem(task);
    }

    @Override
    public void updateTask(TaskItem task) {
        taskAdapter.updateTask(task);
    }

    @Override
    public void showConfirmDialog(String message, TaskItem task) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tarea seleccionada")
                .setMessage(message)
                .setPositiveButton("Sí", (dialog, which) -> presenterTask.updateTask(task))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void showDeleteDialog(String message, TaskItem task) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tarea seleccionada")
                .setMessage(message)
                .setPositiveButton("Sí", (dialog, which) -> presenterTask.deleteTask(task))
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void deleteTask(TaskItem task) {
        taskAdapter.removeTask(task);

    }

    @Override
    public void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void showLogoutMessage() {
        Toast.makeText(TasksActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        goToLogin();
    }


}