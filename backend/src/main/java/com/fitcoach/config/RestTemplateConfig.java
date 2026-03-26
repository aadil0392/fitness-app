package com.fitcoach.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(
            @Value("${fitcoach.http.user-agent:FitCoach/1.0 (https://github.com) Java}") String userAgent) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(8_000);
        factory.setReadTimeout(30_000);

        RestTemplate rt = new RestTemplate(factory);
        ClientHttpRequestInterceptor ua = (request, body, execution) -> {
            request.getHeaders().set("User-Agent", userAgent);
            return execution.execute(request, body);
        };
        rt.setInterceptors(List.of(ua));
        return rt;
    }
}
