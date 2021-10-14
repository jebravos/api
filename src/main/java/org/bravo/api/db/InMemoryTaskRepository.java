package org.bravo.api.db;

import org.bravo.api.entity.Task;
import org.bravo.api.task.TaskRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Scope("singleton")
public class InMemoryTaskRepository implements TaskRepository {

    private final Set<Task> tasks = Collections.synchronizedSet(new HashSet<Task>());

    @Override
    public Task save(Task task) {
        this.tasks.add(task);
        return task;
    }

    @Override
    public Set<Task> findAll() {
        return Set.copyOf(tasks);
    }

    @Override
    public Task update(final Task task) {

        synchronized (tasks){
            this.tasks.stream()
                    .filter(t -> t.getId().equals(task.getId()))
                    .findAny()
                    .ifPresent(existentTask  -> updateTask(task, existentTask));

        }

        return task;
    }

    @Override
    public Set<Task> findBy(Predicate<Task> predicate) {
        synchronized (this.tasks){
            return this.tasks.stream()
                    .filter(predicate)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Optional<Task> findOneBy(Predicate<Task> predicate) {
        synchronized (this.tasks){
            return this.tasks.stream()
                    .filter(predicate)
                    .findFirst();
        }
    }

    private void updateTask(Task task, Task existentTask) {
        this.tasks.remove(existentTask);
        this.tasks.add(task);
    }
}
