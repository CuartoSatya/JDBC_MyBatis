package raisetech.student.management.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentConverterTest {
    private StudentConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StudentConverter();
    }

    @Test
    void convertStudentDetails_受講生と受講生コースが正しく紐づくこと() {
        // Arrange（準備）
        Student student1 = new Student();
        student1.setNumericId(1);

        Student student2 = new Student();
        student2.setNumericId(2);

        List<Student> students = List.of(student1, student2);

        StudentCourse course1 = new StudentCourse();
        course1.setStudentId(1);

        StudentCourse course2 = new StudentCourse();
        course2.setStudentId(1);

        StudentCourse course3 = new StudentCourse();
        course3.setStudentId(2);

        List<StudentCourse> courses = List.of(course1, course2, course3);

        // Act（実行）
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        // Assert（検証）
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStudent()).isEqualTo(student1);
        assertThat(result.get(0).getStudentCourseList()).hasSize(2);
        assertThat(result.get(1).getStudent()).isEqualTo(student2);
        assertThat(result.get(1).getStudentCourseList()).hasSize(1);
    }

   @Test
    void convertStudentDetails_受講生コースが存在しない場合は空リストになること() {
        Student student = new Student();
        student.setNumericId(1);

        List<Student> students = List.of(student);
        List<StudentCourse> courses = List.of(); // 空

        List<StudentDetail> result =
            converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudentCourseList()).isEmpty();
    }
}
