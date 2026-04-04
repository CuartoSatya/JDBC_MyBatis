package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Schema(description = "StudentCoursesData")
@Getter
@Setter

public class StudentCourse {

    @NotNull
    private Integer id;
    @NotNull
    private Integer studentId;
    private String courseName;
    private LocalDateTime startDate;
    private LocalDateTime assuredFinishDate;
    private Integer statusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourse that)) return false;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(assuredFinishDate, that.assuredFinishDate) &&
                Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseName, startDate, assuredFinishDate, statusId);
    }
}
