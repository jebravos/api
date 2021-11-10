package org.bravo.api.algos;

import org.bravo.api.entity.Task;
import org.bravo.api.model.AlgoResultResponse;
import org.bravo.api.model.AlgoStatusResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class RepoClient {

    protected final WebClient client;

    protected static final String COMPUTE_TASK = "/compute";

    protected RepoClient(WebClient client) {
        this.client = client;
    }

    public abstract Mono<AlgoStatusResponse> computeTask(Task task) /*throws JsonProcessingException*/;

    public AlgoStatusResponse getStatus(Task task){

        return this.client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + task.getExecutionId() + "/status")
                        .build())
                .retrieve()
                .toEntity(AlgoStatusResponse.class)
                .block()
                .getBody();

    }

    public AlgoResultResponse getResult(Task task) {

        return this.client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + task.getExecutionId() + "/result")
                        .build())
                .retrieve()
                .toEntity(AlgoResultResponse.class)
                .block()
                .getBody();
    }

}
