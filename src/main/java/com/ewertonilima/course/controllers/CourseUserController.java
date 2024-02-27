package com.ewertonilima.course.controllers;

import com.ewertonilima.course.dtos.SubscriptionDto;
import com.ewertonilima.course.models.CourseModel;
import com.ewertonilima.course.services.CourseService;
import com.ewertonilima.course.services.UserService;
import com.ewertonilima.course.specifications.SpecificationTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    final CourseService courseService;
    final UserService userService;

    public CourseUserController(
            CourseService courseService,
            UserService userService
    ) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault(page = 0, size = 10, sort = "userId",
                                                              direction = Sort.Direction.ASC) Pageable pageable,
                                                      @PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(
                SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        //TODO state transfer verification
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
