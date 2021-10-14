package org.bravo.api.workers.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bravo.api.algos.RepoClient;
import org.bravo.api.entity.Task;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.model.AlgoStatusResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class RepoAClient extends RepoClient {


    protected RepoAClient(WebClient clientA) {
        super(clientA);
    }

    public Mono<AlgoStatusResponse> computeTask(Task task) throws JsonProcessingException {

        RepoAClient.RepoAPayload payload = getMapper().readValue(task.getPayload(), RepoAClient.RepoAPayload.class);
        //TODO use task to compute params

        return this.client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(COMPUTE_TASK)
                        .queryParam("laboratory_name", payload.getLaboratoryName())
                        .queryParam("sfdc", payload.getSfdc())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload.getTranches())
//                .bodyValue(List.of(60,30,60))
                .retrieve()
                .bodyToMono(AlgoStatusResponse.class)
                .timeout(Duration.of(5, SECONDS))
                .onErrorMap(Exception.class, ex -> new InternalErrorException("Error", ex));
    }


    private static class RepoAPayload{

        private String laboratoryName;
        private String sfdc;
        private List<Integer> tranches;

        public String getLaboratoryName() {
            return laboratoryName;
        }

        public void setLaboratoryName(String laboratoryName) {
            this.laboratoryName = laboratoryName;
        }

        public String getSfdc() {
            return sfdc;
        }

        public void setSfdc(String sfdc) {
            this.sfdc = sfdc;
        }

        public List<Integer> getTranches() {
            return tranches;
        }

        public void setTranches(List<Integer> tranches) {
            this.tranches = tranches;
        }
    }

}
