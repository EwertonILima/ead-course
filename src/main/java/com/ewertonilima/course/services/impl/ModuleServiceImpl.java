package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.repositories.ModuleRepository;
import com.ewertonilima.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;
}
