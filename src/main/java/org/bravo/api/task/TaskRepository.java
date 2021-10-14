package org.bravo.api.task;

import org.bravo.api.entity.Task;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public interface TaskRepository {

    Task save(Task task);

    Set<Task> findAll();

    Task update(Task task);

    Set<Task> findBy(Predicate<Task> predicate);

    Optional<Task> findOneBy(Predicate<Task> predicate);

}

