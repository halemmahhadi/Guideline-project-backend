package hh.getData.guideline.Course;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/courses/")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("getAll/")
    public ResponseEntity<List<CourseDto>> getAll() {
        try {
            List<Course> courses = courseService.getAllCourses();
            List<CourseDto> courseDto = courses.stream()
                    .map(CourseDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(courseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        try {
            Course course = courseService.getCourseById(id);
            if (course != null) {
                return new ResponseEntity<>(CourseDto.from(course), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = courseService.createCourse(Course.from(courseDto));
            return new ResponseEntity<>(CourseDto.from(course), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(courseService.deleteCourse(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<CourseDto> editCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        try {
            Course editedCourse = courseService.updateCourse(id, Course.from(courseDto));
            return new ResponseEntity<>(CourseDto.from(editedCourse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<CourseDto> getCourseByName(@PathVariable("name") String courseName) {
        try {
            Course course = courseService.getCourseByName(courseName);
            if (course != null) {
                return new ResponseEntity<>(CourseDto.from(course), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("inactive/id/{id}")
    public ResponseEntity<String> changeStatusToInactive(@PathVariable Long id) {
        try {
            courseService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            courseService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllCourses() {
        try {
            courseService.deleteAllCourses();
            return new ResponseEntity<>("All courses have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all courses.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
