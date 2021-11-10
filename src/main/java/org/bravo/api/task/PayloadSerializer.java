package org.bravo.api.task;

public interface PayloadSerializer {

    TaskPayload serialize(String payload);
}
