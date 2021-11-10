package org.bravo.api.workers.repo_a;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bravo.api.algos.RepoClient;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.task.PayloadSerializer;
import org.bravo.api.task.SchedulerWorker;
import org.bravo.api.task.TaskService;
import org.bravo.api.workers.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WorkerAConfig {

    @Bean
    public WebClient clientA(@Value("${repo.a.url}") String repoABaseUrl){
        return  WebClient.builder().baseUrl(repoABaseUrl).build();
    }

    @Bean
    @Scope("singleton")
    public SchedulerWorker workerA(TaskService taskService, RepoClient repoAClient){
        return new Worker(taskService, repoAClient);
    }

    @Bean
    public PayloadSerializer workerASerializer(){
        return payload -> {
            try {
                PayloadA payloadA = payloadMapper().readValue(payload, PayloadA.class);

                if (payloadA == null) {
                    throw new InternalErrorException("Payload serialization error");
                }

                if (payloadA.getLaboratoryName() == null || payloadA.getLaboratoryName().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: laboratory_name required");
                }

                if (payloadA.getSfdc() == null || payloadA.getSfdc().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: sfdc required");
                }

                if (payloadA.getTranches() == null || payloadA.getTranches().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: tranches required");
                }

                return payloadA;
            } catch (JsonProcessingException e) {
                throw new InternalErrorException("Payload serialization error", e);
            }
        };
    }

    private ObjectMapper payloadMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }

}
