package org.bravo.api.task;

import org.bravo.api.entity.Task;

public interface SchedulerWorker {

    void schedule(Task task);
}
