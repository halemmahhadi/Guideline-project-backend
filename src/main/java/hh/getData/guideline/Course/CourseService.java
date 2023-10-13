package hh.getData.guideline.Course;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(Course newCourse) {
        log.info("Saving new Course information: {}", newCourse.getCourseName());
        // You can set default values or perform any other logic here before saving
        return courseRepository.save(newCourse);
    }

    public List<Course> getAllCourses() {
        log.info("Fetching all Courses");
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        log.info("Fetching Course by ID: {}", id);
        Optional<Course> course = courseRepository.findById(id);
        return course.orElseThrow(() -> new EntityNotFoundException("Course with ID " + id + " not found"));
    }

    public Course updateCourse(Long id, Course courseUpdate) {
        log.info("Updating Course information with ID {}: {}", id, courseUpdate.getCourseName());

        Optional<Course> existingCourseOptional = courseRepository.findById(id);

        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            // Update attributes with new values
            existingCourse.setCourseName(courseUpdate.getCourseName());
            existingCourse.setCourseDescription(courseUpdate.getCourseDescription());
            // Save the updated entity
            return courseRepository.save(existingCourse);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Course with ID " + id + " not found");
        }
    }

    public boolean deleteCourse(Long id) {
        log.info("Deleting Course by ID: {}", id);
        courseRepository.deleteById(id);
        return true;
    }

    public Course getCourseByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Course with ID " + id + " not found"));

            if (course.getStatus() != Status.INACTIVE) {
                course.setStatus(Status.INACTIVE);
                courseRepository.save(course);
            } else {
                throw new StatusAlreadyInactiveException("Course with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Course with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Course with ID " + id + " not found"));

            if (course.getStatus() != Status.ACTIVE) {
                course.setStatus(Status.ACTIVE);
                courseRepository.save(course);
            } else {
                throw new StatusAlreadyInactiveException("Course with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Course with ID " + id, e);
        }
    }

    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}
