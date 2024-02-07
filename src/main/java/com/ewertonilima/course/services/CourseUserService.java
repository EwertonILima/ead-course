package com.ewertonilima.course.services;

import com.ewertonilima.course.models.CourseModel;
import com.ewertonilima.course.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel save(CourseUserModel courseUserModel);
}
