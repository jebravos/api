package org.bravo.api.task;


import org.bravo.api.algos.AlgorithmService;
import org.bravo.api.entity.Algorithm;
import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TaskScheduler {

    private final AlgorithmService algorithmService;
    private final TaskService taskService;
    private final Map<String, SchedulerWorker> workers;


    public TaskScheduler(AlgorithmService algorithmService,
                         TaskService taskService,
                         Map<String, SchedulerWorker> workers) {

        this.algorithmService = algorithmService;
        this.taskService = taskService;
        this.workers = workers;
    }


    public String createNewTask(final String algorithmId, final String payload) {

        Algorithm algorithm = algorithmService.findAlgorithmById(algorithmId);
        Task task = taskService.save(algorithm.getId(), payload);
        schedule(task);

        return task.getId().toString();
    }

    private void schedule(Task task) {

        Optional.ofNullable(workers.get(task.getAlgoId()))
                .orElseThrow(() -> new NotFoundException("Algorithm " + task.getAlgoId() + " not supported!"))
                .schedule(task);

        System.out.println("task scheduled!");
    }

}
