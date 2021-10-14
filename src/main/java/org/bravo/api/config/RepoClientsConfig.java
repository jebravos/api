package org.bravo.api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration()
public class RepoClientsConfig {

    @Bean
    public WebClient clientA(@Value("${repo.a.url}") String repoABaseUrl){
        return  WebClient.builder().baseUrl(repoABaseUrl).build();
    }
    @Bean
    public WebClient clientB(@Value("${repo.b.url}") String repoABaseUrl){
        return  WebClient.builder().baseUrl(repoABaseUrl).build();
    }

}
