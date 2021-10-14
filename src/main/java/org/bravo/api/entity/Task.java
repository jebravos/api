package org.bravo.api.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public class Task {

    private final Long id;
    private final LocalDateTime timestamp;
    private final String algoId;
    private final String payload;
    private String executionId;
    private Status status;
    private String resultFileName;

    public static Task newTask(String algoId, String payload){
        Task task = new Task(algoId, payload);
        task.setStatus(Task.Status.TODO);
        return task;
    }

    private Task(String algoId, String payload){
        this.algoId = algoId;
        this.payload = payload;
        this.id = TaskSequence.nextSequence();
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPayload() {
        return payload;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Long getId() {
        return id;
    }

    public String getAlgoId() {
        return algoId;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public static Predicate<Task> id(Long id) {
        return task -> task.getId().equals(id);
    }

    public static Predicate<Task> algorithmId(String algorithmId) {
        return task -> task.getAlgoId().equals(algorithmId);
    }

    public static Predicate<Task> algorithmIdAndExecutionId(String algorithmId, String executionId) {
        return task -> task.getAlgoId().equals(algorithmId) && task.getExecutionId().equals(executionId);
    }

    public enum Status {
        TODO, DOING, DONE, ERROR, TIMEOUT
    }

    private static class TaskSequence{

        private static AtomicLong currentIndex = new AtomicLong(1);

        public static Long nextSequence(){

            return currentIndex.getAndIncrement();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
