package org.bravo.api.workers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bravo.api.algos.RepoClient;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.entity.Task;
import org.bravo.api.task.SchedulerWorker;
import org.bravo.api.task.TaskService;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractWorker implements SchedulerWorker {

    private final TaskService taskService;

    protected AbstractWorker(TaskService taskService) {
        this.taskService = taskService;
    }

    public void schedule(Task task){
        getWorker().submit(() -> {
            try {
                computeTask(task);
            } catch (InterruptedException | JsonProcessingException e) {
                throw new InternalErrorException("Error scheduling the task " + task.getId(), e);
            }
        });

    }

    private void computeTask(Task task) throws InterruptedException, JsonProcessingException {

        System.out.println(Thread.currentThread().getName() + "Working in Task " + task.getId());

        getClient().computeTask(task)
                .onErrorResume(e -> {
                    taskService.taskError(task);
                    return Mono.empty();
                }).blockOptional()
                .ifPresent(response -> {
                    Task doingTask = taskService.doingTask(task, response.id());
                    waitForCompletion(doingTask);
                    taskService.taskDone(doingTask);
                });

    }

    private void waitForCompletion(Task doingTask)  {

        do {
            System.out.println(Thread.currentThread().getName() + "Waiting execution...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new InternalErrorException("Error waiting for completion of the task " + doingTask.getId(), e);
            }
        } while (!getClient().getStatus(doingTask).state().equals("DONE"));
    }

    protected abstract RepoClient getClient();

    protected abstract ExecutorService getWorker();

}
