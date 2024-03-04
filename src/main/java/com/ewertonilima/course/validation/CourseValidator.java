package com.ewertonilima.course.validation;

import com.ewertonilima.course.dtos.CourseDto;
import com.ewertonilima.course.enums.UserType;
import com.ewertonilima.course.models.UserModel;
import com.ewertonilima.course.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    final private Validator validator;
    final UserService userService;

    public CourseValidator(
            @Qualifier("defaultValidator")
            Validator validator,
            UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDto courseDto = (CourseDto) o;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        Optional<UserModel> userModelOptional = userService.findById(userInstructor);
        if (!userModelOptional.isPresent()) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor Not Found.");
        }
        if (userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())) {
            errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");

        }
    }
}
