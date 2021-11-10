package org.bravo.api.workers.client;

import org.bravo.api.algos.RepoClient;
import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.model.AlgoStatusResponse;
import org.bravo.api.task.PayloadB;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class RepoBClient extends RepoClient {

    public RepoBClient(WebClient clientB) {
        super(clientB);
    }

    @Override
    public Mono<AlgoStatusResponse> computeTask(Task task)  {

        PayloadB payload = (PayloadB) task.getPayload();

        return this.client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(COMPUTE_TASK)
                        .queryParam("laboratory_name", payload.getLaboratoryName())
                        .queryParam("identification_column", payload.getIdentificationColumn())
                        .queryParam("input_file", payload.getInputFile())
                        .build())
                .retrieve()
                .bodyToMono(AlgoStatusResponse.class)
                .timeout(Duration.of(5, SECONDS))
                .onErrorMap(Exception.class, ex -> new InternalErrorException("Error", ex));
    }

}
