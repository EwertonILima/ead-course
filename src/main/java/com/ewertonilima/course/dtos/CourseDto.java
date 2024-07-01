package com.ewertonilima.course.dtos;

import com.ewertonilima.course.enums.CourseLevel;
import com.ewertonilima.course.enums.CourseStatus;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private UUID userInstructor;
    @NotNull
    private CourseLevel courseLevel;
}
