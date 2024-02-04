package com.ewertonilima.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
    String createUrl(UUID courseId, Pageable pageable);
}