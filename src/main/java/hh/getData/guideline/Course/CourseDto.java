package hh.getData.guideline.Course;

import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;

    @NotBlank
    @NotNull
    private String courseName;

    private String courseDescription;

    private Status status = Status.ACTIVE;

    public static CourseDto from(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setCourseDescription(course.getCourseDescription());
        courseDto.setStatus(course.getStatus());
        return courseDto;
    }
}
