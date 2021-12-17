package com.isamaru.tasklist.presenter;

import com.isamaru.tasklist.model.TaskInteractor;
import com.isamaru.tasklist.mvp.TaskMVP;
import com.isamaru.tasklist.view.dto.TaskItem;
import com.isamaru.tasklist.view.dto.TaskState;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskPresenter implements TaskMVP.PresenterTask {

    //Inicializo el modelo y la vista, pues necesito comunicarme con ellos para mostrar las tareas
    private TaskMVP.ModelTask modelTask;
    private TaskMVP.ViewTask viewTask;

    public TaskPresenter(TaskMVP.ViewTask viewTask){ //constructor de la clase TaskPresenter
        this.modelTask = new TaskInteractor(); // inicializo  objeto tipo Model (Interactor)
        this.viewTask = viewTask; // para referenciar la vista correctamente le pedimos la vista como parámetro
    }

    @Override
    public void loadTasks() {

        //Cargar las tareas que ya teníamos definidas, traidas del model
        List<TaskItem> items = modelTask.getTasks();

        //Decirle a la vista que muestre las tareas que trajimos de model
        viewTask.showTaskList(items);
    }

    @Override
    public void addNewTask() {
        //Le pido a la vista que me dé la descripción de la tarea
        String description =  viewTask.getTaskDescription();
        String date = SimpleDateFormat.getDateTimeInstance().format(new Date());

        // Creo el contenido del item que quiero mostrar
        TaskItem task = new TaskItem(description, date);

        // Le digo al modelo que guarde esa tarea

        modelTask.saveTask(task);
        // Ledigo a la vista que muestre la tarea
        viewTask.addTaskToList(task);


    }

    @Override
    public void taskItemClicked(TaskItem item) {
        item.setState(TaskState.DONE);

        modelTask.updateTask(item);
        viewTask.updateTask(item);

    }
}
