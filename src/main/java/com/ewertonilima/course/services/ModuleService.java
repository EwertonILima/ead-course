package com.ewertonilima.course.services;

import com.ewertonilima.course.models.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    void deleteModule(ModuleModel moduleModel);

    ModuleModel save(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<ModuleModel> findAllByCourse(UUID courseId);

    Optional<ModuleModel> findById(UUID moduleId);
}
