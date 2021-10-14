package org.bravo.api.workers.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bravo.api.algos.RepoClient;
import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.model.AlgoStatusResponse;
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
    public Mono<AlgoStatusResponse> computeTask(Task task) throws JsonProcessingException {

        RepoBPayload repoBPayload = getMapper().readValue(task.getPayload(), RepoBPayload.class);

        return this.client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(COMPUTE_TASK)
                        .queryParam("laboratory_name", repoBPayload.getLaboratoryName())
                        .queryParam("identification_column", repoBPayload.getIdentificationColumn())
                        .queryParam("input_file", repoBPayload.getInputFile())
                        .build())
                .retrieve()
                .bodyToMono(AlgoStatusResponse.class)
                .timeout(Duration.of(5, SECONDS))
                .onErrorMap(Exception.class, ex -> new InternalErrorException("Error", ex));
    }


    private static class RepoBPayload{

        private String laboratoryName;
        private String identificationColumn;
        private String inputFile;

        public String getLaboratoryName() {
            return laboratoryName;
        }

        public void setLaboratoryName(String laboratoryName) {
            this.laboratoryName = laboratoryName;
        }

        public String getIdentificationColumn() {
            return identificationColumn;
        }

        public void setIdentificationColumn(String identificationColumn) {
            this.identificationColumn = identificationColumn;
        }

        public String getInputFile() {
            return inputFile;
        }

        public void setInputFile(String inputFile) {
            this.inputFile = inputFile;
        }
    }
}
