package com.ewertonilima.course.clients;

import com.ewertonilima.course.dtos.ResponsePageDto;
import com.ewertonilima.course.dtos.UserDto;
import com.ewertonilima.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    final RestTemplate restTemplate;
    final UtilsService utilsService;

    public AuthUserClient(RestTemplate restTemplate, UtilsService utilsService) {
        this.restTemplate = restTemplate;
        this.utilsService = utilsService;
    }

    @Value("${ead.api.url.authuser}")
    String REQUEST_URL_AUTHUSER;

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDto> searchResult;
        String url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType =
                    new ParameterizedTypeReference<>() {
                    };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();
    }
}
