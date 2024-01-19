package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.repositories.LessonRepository;
import com.ewertonilima.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;
}
