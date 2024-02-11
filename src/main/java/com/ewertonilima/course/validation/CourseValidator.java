package com.ewertonilima.course.validation;

import com.ewertonilima.course.clients.AuthUserClient;
import com.ewertonilima.course.dtos.CourseDto;
import com.ewertonilima.course.dtos.UserDto;
import com.ewertonilima.course.enums.UserType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    final private Validator validator;
    final AuthUserClient authUserClient;

    public CourseValidator(
            @Qualifier("defaultValidator")
            Validator validator,
            AuthUserClient authUserClient
    ) {
        this.validator = validator;
        this.authUserClient = authUserClient;
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
        ResponseEntity<UserDto> responseUserInstructor;
        try {
            responseUserInstructor = authUserClient.getOneUserById(userInstructor);
            if (responseUserInstructor.getBody().getUserType().equals(UserType.STUDENT)) {
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
            }
        } catch (HttpStatusCodeException e) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor Not Found.");
        }
    }
}
