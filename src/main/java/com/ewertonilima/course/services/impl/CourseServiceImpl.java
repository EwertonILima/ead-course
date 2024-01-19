package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.repositories.CourseRepository;
import com.ewertonilima.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
}
