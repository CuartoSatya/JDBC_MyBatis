package raisetech.student.management.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.StudentCourse;

import java.util.List;

    @Mapper
    public interface StudentCoursesRepository {
        @Select("SELECT id, student_id AS studentId, name AS name, starting_date AS startingDate, " +
                "assured_finishing_date AS assuredFinishingDate FROM student_courses")
        List<StudentCourse> searchAll();
    }
