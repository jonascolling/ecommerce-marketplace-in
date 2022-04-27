package com.ecommerce.marketplacein.service.httpService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpServiceImpl implements HttpService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = LogManager.getLogger(HttpServiceImpl.class);

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, Object body, Class<T> responseType) {
        ResponseEntity<T> response = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            response = restTemplate.exchange(url, method, new HttpEntity<>(body, headers), responseType);
        } catch (HttpClientErrorException error) {
            throw error;
        }

        return response;

    }
}
