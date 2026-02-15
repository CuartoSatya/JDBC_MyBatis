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
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime assuredFinishDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourse)) return false;
        StudentCourse that = (StudentCourse) o;
        return Objects.equals(studentId, that.studentId)&&
                Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(assuredFinishDate, that.assuredFinishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, startDate, assuredFinishDate);
    }
}
