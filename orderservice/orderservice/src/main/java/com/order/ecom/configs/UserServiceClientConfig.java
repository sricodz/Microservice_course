package com.order.ecom.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public UserServiceClient userServiceClientInterface(RestClient.Builder restClient){
        RestClient client = restClient
                .baseUrl("http://localhost:8081")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        ((req,res)-> Optional.empty()))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(UserServiceClient.class);
    }
}
