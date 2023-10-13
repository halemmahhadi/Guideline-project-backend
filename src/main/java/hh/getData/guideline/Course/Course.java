package hh.getData.guideline.Course;

import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq", initialValue = 1)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_des")
    private String courseDescription;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public static Course from(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setCourseDescription(courseDto.getCourseDescription());
        course.setStatus(courseDto.getStatus());
        return course;
    }
}
