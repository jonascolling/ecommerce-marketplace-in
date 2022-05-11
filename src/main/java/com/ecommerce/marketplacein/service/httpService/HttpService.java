package com.ecommerce.marketplacein.service.httpService;

import com.ecommerce.marketplacein.service.authentication.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface HttpService {

    <T> ResponseEntity<T> exchange(String url, HttpMethod method, Object body, HttpHeaders httpHeaders, Class<T> responseType);

}
