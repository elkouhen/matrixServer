package com.softeam.springconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Created by elkouhen on 26/03/15.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public HttpClient httpClient() {

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        final CloseableHttpClient client = httpClientBuilder.build();

        return client;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }
}
