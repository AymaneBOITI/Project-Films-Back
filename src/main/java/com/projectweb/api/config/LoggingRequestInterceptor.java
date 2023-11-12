package com.projectweb.api.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws java.io.IOException {

        logRequestDetails(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        System.out.println("URI: " + request.getURI());
        System.out.println("HTTP Method: " + request.getMethod());
        System.out.println("HTTP Headers: " + request.getHeaders());
        System.out.println("Request Body: " + new String(body, StandardCharsets.UTF_8));
    }

}

