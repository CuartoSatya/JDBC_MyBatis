package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Schema(description = "StudentCoursesData")
@Getter
@Setter
public class StudentCourse {

    private Integer id;
    private Integer studentId;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime assuredFinishDate;
}
