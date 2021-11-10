package org.bravo.api.workers.client;

import org.bravo.api.algos.RepoClient;
import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.model.AlgoStatusResponse;
import org.bravo.api.task.PayloadA;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class RepoAClient extends RepoClient {


    protected RepoAClient(WebClient clientA) {
        super(clientA);
    }

    public Mono<AlgoStatusResponse> computeTask(Task task) {

        PayloadA payload = (PayloadA) task.getPayload();

        return this.client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(COMPUTE_TASK)
                        .queryParam("laboratory_name", payload.getLaboratoryName())
                        .queryParam("sfdc", payload.getSfdc())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload.getTranches())
                .retrieve()
                .bodyToMono(AlgoStatusResponse.class)
                .timeout(Duration.of(5, SECONDS))
                .onErrorMap(Exception.class, ex -> new InternalErrorException("Error", ex));
    }

}
