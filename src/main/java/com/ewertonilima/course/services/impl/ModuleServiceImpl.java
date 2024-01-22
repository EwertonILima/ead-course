package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.models.LessonModel;
import com.ewertonilima.course.models.ModuleModel;
import com.ewertonilima.course.repositories.LessonRepository;
import com.ewertonilima.course.repositories.ModuleRepository;
import com.ewertonilima.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void deleteModule(ModuleModel moduleModel) {
        List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if (!lessonModelList.isEmpty()) {
            lessonRepository.deleteAll(lessonModelList);
        }
        moduleRepository.delete(moduleModel);
    }
}
