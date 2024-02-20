package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.clients.AuthUserClient;
import com.ewertonilima.course.models.CourseModel;
import com.ewertonilima.course.models.CourseUserModel;
import com.ewertonilima.course.models.LessonModel;
import com.ewertonilima.course.models.ModuleModel;
import com.ewertonilima.course.repositories.CourseRepository;
import com.ewertonilima.course.repositories.CourseUserRepository;
import com.ewertonilima.course.repositories.LessonRepository;
import com.ewertonilima.course.repositories.ModuleRepository;
import com.ewertonilima.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    final CourseUserRepository courseUserRepository;

    final AuthUserClient authUserClient;

    public CourseServiceImpl(CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void deleteCourse(CourseModel courseModel) {
        boolean deleteCourseUserInAuthUser = false;
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()) {
            for (ModuleModel module : moduleModelList) {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
        if (!courseUserModelList.isEmpty()) {
            courseUserRepository.deleteAll(courseUserModelList);
            deleteCourseUserInAuthUser = true;
        }
        courseRepository.delete(courseModel);
        if (deleteCourseUserInAuthUser) {
            authUserClient.deleteCourseInAuthUser(courseModel.getCourseId());
        }

    }

}
