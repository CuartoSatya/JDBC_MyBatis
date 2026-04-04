package raisetech.student.management.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.dto.UpdateStatusCourseRequest;
import raisetech.student.management.data.StatusCourses;
import raisetech.student.management.data.StudentCoursesSearchResult;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

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
    List<Student> findAllStudent();

    /**
     * 受講生の検索を行います。
     * @param id　受講生ID
     * @return 受講生
     */
    Student findStudentById(Integer id);

    /**
     * 受講生のコース情報の全件検索を行います。
     * @return 受講生のコース情報（全件）
     */
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     * @param studentId　受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> searchStudentCourse(Integer studentId);

    /**
     * 受講生コース情報の一覧画面を表示します。
     */
    List<StudentCourse> searchAllStudentCourses();

    /**
     * 受講生コース情報を検索します。
     *
     * @param name 生徒名
     * @param courseName 受講コース名
     * @return 受講コース情報
     */
    List<StudentCoursesSearchResult> searchStudents(
            @Param("name") String name,
            @Param("courseName") String courseName);

    Optional<Student> findById(Integer id);

    /**
     * 受講生を新規登録します。 IDに関しては自動採番を行う。
     *
     * @param student 受講生
     */
    void insertStudent(Student student);

    /**
     * 受講生を新規登録します。 IDに関しては自動採番を行う。
     *
     * @param studentsCourse 受講生コース情報
     */
    void insertStudentCourse(StudentCourse studentsCourse);

    /**
     * 新規登録時に受講コースの申込状況を追加する。
     *
     * @param statusCourses 受講コース申込状況
     */
    void insertStatusCourse(StatusCourses statusCourses);

    /**
     * 受講生を更新します。
     *
     * @param student 受講生
     */
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講コース名
     */
    void updateStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生コース情報の申し込み状況を更新します。
     *
     * @param request　受講コースの申込状況など
     */
    void updateStatusCourse(UpdateStatusCourseRequest request);
}
