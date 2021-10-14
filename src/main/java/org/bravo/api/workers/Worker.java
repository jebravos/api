package org.bravo.api.workers;

import org.bravo.api.algos.RepoClient;
import org.bravo.api.task.TaskService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker extends AbstractWorker {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final RepoClient repoClient;

    public Worker(TaskService taskService, RepoClient repoClient) {
        super(taskService);
        this.repoClient = repoClient;
    }

    @Override
    protected RepoClient getClient() {
        return repoClient;
    }

    protected ExecutorService getWorker(){
        return this.executorService;
    }

}
