package raisetech.student.management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, address, age, " +
            "sex FROM student")
    List<Student> searchStudent();

   @Select("SELECT id, student_id AS studentId, name, starting_date AS startingDate, assured_finishing_date " +
            "AS assuredFinishingDate FROM students_courses")
    List<StudentsCourses> searchStudentCourses();
}
