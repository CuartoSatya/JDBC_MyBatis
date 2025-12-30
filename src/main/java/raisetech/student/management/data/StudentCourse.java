package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "StudentCoursesData")
@Getter
@Setter
public class StudentCourse {

    @NotNull
    private Integer id;
    @NotNull
    private Integer studentId;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime assuredFinishDate;
}
