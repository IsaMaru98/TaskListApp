package com.isamaru.tasklist.view;

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

    //variable para llamar el metodo del presenter
    private TaskMVP.PresenterTask presenter;


    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        presenter = new TaskPresenter(TasksActivity.this);

        initUI();
        presenter.loadTasks();

        btnCerrar = (Button) findViewById(R.id.btnLogOut);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();


                Toast.makeText(TasksActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                goToLogin();

            }
        });//Fin boton cerrar sesión
    }//Fin onCreate

    private void initUI() {
        tilNewTask = findViewById(R.id.til_new_task);
        tilNewTask.setEndIconOnClickListener(v -> presenter.addNewTask());

         /* Para que aparezca un Toast diciendo que se agragó tal tarea
        tilNewTask.setEndIconOnClickListener(v -> {



            String description =  etNewTask.getText().toString();

            Toast.makeText(TasksActivity.this, "Task " + description+" Added", Toast.LENGTH_SHORT)
                    .show();
        });*/

        etNewTask = findViewById(R.id.et_new_task);

        taskAdapter = new TaskAdapter();
        taskAdapter.setlistener(item -> presenter.taskItemClicked(item));
        rvTasks = findViewById(R.id.rv_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(TasksActivity.this));
        rvTasks.setAdapter(taskAdapter);
    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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


}