package org.bravo.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bravo.api.algos.RepoClient;
import org.bravo.api.exceptions.InternalErrorException;
import org.bravo.api.task.PayloadB;
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
public class WorkerBConfig {

    @Bean
    public WebClient clientB(@Value("${repo.b.url}") String repoABaseUrl){
        return  WebClient.builder().baseUrl(repoABaseUrl).build();
    }

    @Bean
    @Scope("singleton")
    public SchedulerWorker workerB(TaskService taskService, RepoClient repoBClient){
        return new Worker(taskService, repoBClient);
    }


    @Bean
    public PayloadSerializer workerBSerializer(){
        return payload -> {
            try {

                PayloadB payloadB = payloadMapper().readValue(payload, PayloadB.class);

                if (payloadB == null) {
                    throw new InternalErrorException("Payload serialization error");
                }

                if (payloadB.getLaboratoryName() == null || payloadB.getLaboratoryName().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: laboratory_name required");
                }

                if (payloadB.getIdentificationColumn() == null || payloadB.getIdentificationColumn().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: identification column required");
                }


                if (payloadB.getInputFile() == null || payloadB.getInputFile().isEmpty()) {
                    throw new InternalErrorException("Payload serialization error: input_file  required");
                }

                return payloadB;
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
