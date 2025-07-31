package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.StudentsCourses;

import java.util.List;

    @Mapper
    public interface StudentsCoursesRepository {
        @Select("SELECT id, student_id AS studentId, name, starting_date AS startingDate, assured_finishing_date " +
                "AS assuredFinishingDate FROM students_courses")
        List<StudentsCourses> searchAll();
    }
