package raisetech.student.management.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, " +
            "address, age, sex, remark, deleted FROM student")
    List<Student> searchStudent();

    @Select("SELECT id, student_id AS studentId, name, starting_date AS startingDate, " +
            "assured_finishing_date AS assuredFinishingDate FROM student_courses")
    List<StudentsCourses> searchStudentsCourses();

    @Insert("INSERT INTO student (name, kana_name, nickname, mail_address, address, age, sex, remark, deleted) " +
            "VALUES (#{name}, #{kanaName}, #{nickname}, #{mailAddress}, #{address}, #{age}, #{sex}, #{remark}," +
            " #{deleted})")

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id_student")
    void insertStudent(Student student);

    @Insert("INSERT INTO student_courses (student_id, name, starting_date, assured_finishing_date) " +
            "VALUES (#{studentId}, #{name}, #{startingDate}, #{assuredFinishingDate})")
    void insertStudentCourse(StudentsCourses sc);

}
