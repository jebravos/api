package org.bravo.api.task;

import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(final String algorithmId, final String payload){
        Task task = Task.newTask(algorithmId, payload);
        return taskRepository.save(task);
    }

    public Task doingTask(Task task, String executionId){

        System.out.println("Updating task " + task.getId() + " to status DOING");

        task.setExecutionId(executionId);
        task.setStatus(Task.Status.DOING);
        return update(task);
    }

    public Task taskDone(Task task){

        System.out.println("Updating task " + task.getId() + " to status DONE");

        task.setStatus(Task.Status.DONE);
        return update(task);
    }

    public Task taskError(Task task){

        System.out.println("Updating task " + task.getId() + " to status ERROR");

        task.setStatus(Task.Status.ERROR);
        return update(task);
    }

    private Task update(Task task){
        System.out.println("Updating task " + task.getId());
        return taskRepository.update(task);
    }

    public Set<Task> findByAlgorithmId(String algorithmId){
        return taskRepository.findBy(Task.algorithmId(algorithmId));
    }

    public Task findByAlgorithmAndTaskId(String algorithmId, String taskId){

        return taskRepository.findOneBy(Task.algorithmId(algorithmId))
                .orElseThrow(NotFoundException.newNotFoundException("Task " + taskId + " for algorithm " + algorithmId + " was not found!"));
    }

    public void uploadResult(final String algorithmId,
                             final String executionId,
                             final String resultName) {

        taskRepository.findOneBy(Task.algorithmIdAndExecutionId(algorithmId, executionId))
                .ifPresentOrElse(task -> uploadResult(resultName, task),
                        NotFoundException.throwNotFoundException("task with executionId " + executionId + " for worker " + algorithmId + " wasn't found!"));
    }

    public String downloadResult(final String taskId) {

        return taskRepository.findOneBy(Task.id(Long.valueOf(taskId)))
                .map(Task::getResultFileName)
                .map(resultFile -> "downloading " + resultFile)
                .orElseThrow(NotFoundException.newNotFoundException("Result not available yet for task " + taskId));
    }


    private void uploadResult(String resultName, Task task) {
        task.setResultFileName(resultName);
        taskRepository.update(task);
    }



}
