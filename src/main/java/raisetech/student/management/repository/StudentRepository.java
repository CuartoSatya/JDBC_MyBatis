package raisetech.student.management.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourses;

import java.util.List;
import java.util.Optional;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

    @Transactional

    /**
     * 受講生の全件検索を行います。
     *
     * @return 受講生情報（全件）
     */
    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, " +
            "address, age, sex, remark, deleted FROM student WHERE deleted = false")
    List<Student> findAllStudent();

    /**
     * 受講生の検索を行います。
     * @param id　受講生ID
     * @return　受講生
     */
    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, " +
            "address, age, sex, remark, deleted FROM student WHERE id_student = #{id}")
    Student findStudentById(Integer id);

    /**
     * 受講生のコース情報の全件検索を行います。
     * @return　受講生のコース情報（全件）
     */
    @Select("SELECT id, student_id AS studentId, name, starting_date AS startingDate, " +
            "assured_finishing_date AS assuredFinishingDate FROM student_courses")
    List<StudentCourses> searchStudentCoursesList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     * @param studentId　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */
    @Select("SELECT id, student_id AS studentId, name, starting_date AS startingDate, " +
            "assured_finishing_date AS assuredFinishingDate FROM student_courses WHERE student_id = #{studentId}")
    List<StudentCourses> searchStudentCourses(Integer studentId);

    @Select("SELECT id_student AS id, name, kana_name AS kanaName, nickname, mail_address AS mailAddress, " +
            "address, age, sex, remark, deleted FROM student WHERE id_student = #{id}")
    Optional<Student> findById(Integer id);

    @Insert("INSERT INTO student (name, kana_name, nickname, mail_address, address, age, sex, remark, deleted) " +
            "VALUES (#{name}, #{kanaName}, #{nickname}, #{mailAddress}, #{address}, #{age}, #{sex}, #{remark}," +
            " #{deleted})")

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id_student")
    void insertStudent(Student student);

    @Insert("INSERT INTO student_courses (student_id, name, starting_date, assured_finishing_date) " +
            "VALUES (#{studentId}, #{name}, #{startingDate}, #{assuredFinishingDate})")

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id_student")

    void insertStudentCourse(StudentCourses sc);

    @Update("UPDATE student SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, " +
            "mail_address = #{mailAddress}, address = #{address}, age = #{age}, sex = #{sex}, remark = #{remark}, " +
            "deleted = #{deleted} WHERE id_student = #{id}")

    void updateStudent(Student student);

    @Update("UPDATE student_courses SET name = #{name}, starting_date = #{startingDate}, assured_finishing_date = " +
            "#{assuredFinishingDate} WHERE id = #{id}")
    void updateStudentCourse(StudentCourses sc);
}
