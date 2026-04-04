package raisetech.student.management.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import raisetech.student.management.data.StudentCourse;
import java.util.Optional;

@MybatisTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    /**
     * findAllStudent
     */
    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.findAllStudent();
        assertThat(actual.size()).isEqualTo(6);
    }

    /**
     * insertStudent
     */
    @Test
    void 受講生の登録が行えること() {
        Student student = new Student(
                "A999",                     // id
                "Shimomura Atomu",          // name
                "Simomura Atomu",           // kanaName
                "Atomu",                    // nickname
                "Atoms@lycos.co.jp",        // mailAddress
                "Nagano",                   // address
                25,                         // age
                "1",                        // sex
                false,                      // deleted
                "",                         // remark
                "GraphicCourse"             // course
        );

        sut.insertStudent(student);

        List<Student> actual = sut.findAllStudent();

        assertThat(actual).hasSize(7);
        assertThat(actual)
                .extracting(Student::getName)
                .contains("Shimomura Atomu");
    }

    /**
     * findStudentById
     */
    @Test
    void IDを指定して受講生を1件取得できること() {
        Student actual = sut.findStudentById(1);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Tanaka Taro");
    }

    /**
     * findById（Optional）
     */
    @Test
    void Optionalで受講生を取得できること() {
        Optional<Student> actual = sut.findById(1);

        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("Tanaka Taro");
    }

    @Test
    void 存在しないIDの場合Optionalがemptyになること() {
        Optional<Student> actual = sut.findById(999);

        assertThat(actual).isEmpty();
    }

    /**
     * searchStudentCourseList
     */
    @Test
    void 受講生コース情報の全件検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual)
                .hasSize(7)
                .extracting(StudentCourse::getStudentId)
                .contains(1);
    }

    /**
     * searchStudentCourse
     */
    @Test
    void 受講生IDに紐づくコース情報が取得できること() {
        List<StudentCourse> actual = sut.searchStudentCourse(1);

        StudentCourse expected = new StudentCourse();
        expected.setId(2);
        expected.setStudentId(1);
        expected.setCourseName("AWScourse");
        expected.setStatusId(2);
        expected.setStartDate(null);
        expected.setAssuredFinishDate(null);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(expected));
    }

    /**
     * insertStudentCourse
     */
    @Test
    void 受講生コース情報の登録が行えること() {
        StudentCourse course = new StudentCourse();
        course.setStudentId(1);
        course.setCourseName("NewCourse");
        course.setStartDate(null);
        course.setAssuredFinishDate(null);

        sut.insertStudentCourse(course);

        List<StudentCourse> actual = sut.searchStudentCourse(1);
        assertThat(actual)
                .extracting(StudentCourse::getCourseName)
                .contains("NewCourse");
    }

    /**
     * updateStudent
     */
    @Test
    void 受講生情報の更新が行えること() {
        Student student = sut.findStudentById(1);
        student.setNickname("UpdatedNick");

        sut.updateStudent(student);

        Student updated = sut.findStudentById(1);
        assertThat(updated.getNickname()).isEqualTo("UpdatedNick");
    }

    /**
     * updateStudentCourse
     */
    @Test
    void 受講生コース名の更新が行えること() {
        List<StudentCourse> courses = sut.searchStudentCourse(1);
        StudentCourse course = courses.get(0);
        course.setCourseName("UpdatedCourse");

        sut.updateStudentCourse(course);

        List<StudentCourse> updated = sut.searchStudentCourse(1);
        assertThat(updated)
                .extracting(StudentCourse::getCourseName)
                .contains("UpdatedCourse");
    }

    @Test
    void 受講生コース一覧画面用の全件検索が行えること() {
        List<StudentCourse> actual = sut.searchAllStudentCourses();

        assertThat(actual).isNotEmpty();
        assertThat(actual)
                .extracting(StudentCourse::getCourseName)
                .contains("AWScourse");
    }
}
