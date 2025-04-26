package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

    @Mapper
    public interface StudentCoursesRepository {
        @Select("SELECT id, student_id AS studentid, name, starting_date AS startingDate, assured_finishing_date " +
                "AS assuredFinishingDate FROM student_courses WHERE student_id = #{studentId}")
        List<StudentCourses> search();
    }
