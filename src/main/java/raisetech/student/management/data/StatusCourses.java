package raisetech.student.management.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.constant.CourseProgressStatus;

@Getter
@Setter
@EqualsAndHashCode(of = "idCourse")
public class StatusCourses {


    // student_courses.id と一致
    private Integer idCourse;
    // 進行状態
    private Integer status;

    public StatusCourses() {
    }

    public StatusCourses(Integer idCourse, Integer status) {
        this.idCourse = idCourse;
        this.status = status;
    }

    public CourseProgressStatus getProgressStatus() {
        if (this.status == null) {
            return null;
        }
        // 便利メソッド（表示用）
        return CourseProgressStatus.fromCode(this.status);
    }
}
