package org.bravo.api.algos;

import org.bravo.api.entity.Algorithm;
import org.bravo.api.entity.Task;
import org.bravo.api.model.UploadResultBody;
import org.bravo.api.task.TaskScheduler;
import org.bravo.api.task.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/")
public class AlgorithmController {

    private final TaskScheduler algorithmDispatcher;
    private final AlgorithmService algorithmService;
    private final TaskService taskService;


    public AlgorithmController(TaskScheduler algorithmService, AlgorithmService algorithmService1, TaskService taskService) {
        this.algorithmDispatcher = algorithmService;
        this.algorithmService = algorithmService1;
        this.taskService = taskService;
    }

    @GetMapping("algos")
    public Set<Algorithm> findAll(){
        //TODO il manque le status du worker
        return algorithmService.findAllAlgorithms();
    }

    @GetMapping("{algo}/results/list")
    public Set<Task> findAllByAlgorithm(@PathVariable("algo") String algoName){
        return taskService.findByAlgorithmId(algoName);
    }

    @GetMapping("{algo}/results/{id}")
    public Task getTaskResult(@PathVariable("algo") String algorithmId,
                              @PathVariable("id") String taskId) {

        return taskService.findByAlgorithmAndTaskId(algorithmId, taskId);
    }

    @GetMapping("{algo}/results/{id}/download")
    public String downloadResult(@PathVariable("algo") String algorithmId,
                               @PathVariable("id") String taskId) {

        return taskService.downloadResult(taskId);
    }


    @PostMapping("{algo}/new_task")
    public String newTask(@PathVariable("algo") String algoName,
                          @RequestBody String payload){

        return algorithmDispatcher.createNewTask(algoName, payload);
    }

    @PostMapping("{algo}/results/{executionId}")
    public String uploadResult(@PathVariable("algo") String algorithmId,
                               @PathVariable("executionId") String executionId,
                               @RequestBody UploadResultBody uploadResultBody){

        taskService.uploadResult(algorithmId, executionId, uploadResultBody.getFilename());
        return "OK";
    }
//    --------------------------------------
    @GetMapping("{algo}")
    public Algorithm getAlgoByName(@PathVariable("algo") String algoId){
        return algorithmService.getAlgoBy(algoId);
    }

}
