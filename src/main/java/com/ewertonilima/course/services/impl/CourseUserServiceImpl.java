package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.repositories.CourseUserRepository;
import com.ewertonilima.course.services.CourseUserService;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    final
    CourseUserRepository courseUserRepository;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository) {
        this.courseUserRepository = courseUserRepository;
    }
}
