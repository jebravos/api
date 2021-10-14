package org.bravo.api.config;

import org.bravo.api.algos.RepoClient;
import org.bravo.api.workers.Worker;
import org.bravo.api.task.SchedulerWorker;
import org.bravo.api.task.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class WorkersConfig {

    @Bean
    @Scope("singleton")
    public SchedulerWorker workerA(TaskService taskService, RepoClient repoAClient){
        return new Worker(taskService, repoAClient);
    }

    @Bean
    @Scope("singleton")
    public SchedulerWorker workerB(TaskService taskService, RepoClient repoBClient){
        return new Worker(taskService, repoBClient);
    }
}
