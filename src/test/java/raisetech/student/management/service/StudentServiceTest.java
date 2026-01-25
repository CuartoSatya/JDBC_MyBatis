package raisetech.student.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void setup() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void ToWorkTheFunctionToSearchStudentDetails_ToImportTheProcessOfRepositoryAndConverterPrecisely() {
        // Arrange
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        List<StudentDetail> expected = new ArrayList<>();

        when(repository.findAllStudent()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
        when(converter.convertStudentDetails(studentList, studentCourseList)).thenReturn(expected);

        // Act
        List<StudentDetail> actual = sut.searchStudentList();

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(repository, times(1)).findAllStudent();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    @Test
    void findStudent_searchStudentByIdAndOnesCourse() {
        // Arrange
        Integer id = 1;
        Student student = new Student();
        student.setNumericId(id);
        List<StudentCourse> courseList = List.of(new StudentCourse());

        when(repository.findStudentById(id)).thenReturn(student);
        when(repository.searchStudentCourse(id)).thenReturn(courseList);

        // Act
        StudentDetail actual = sut.findStudent(id);

        // Assert
        assertThat(actual.getStudent()).isEqualTo(student);
        assertThat(actual.getStudentCourseList()).isEqualTo(courseList);
        verify(repository, times(1)).findStudentById(id);
        verify(repository,times(1)).searchStudentCourse(id);
    }

    @Test
    void registerStudentDetail_formatProcess() {
        Integer id = 999;
        Student student = new Student();
        student.setNumericId(id);
        StudentCourse studentCourse = new StudentCourse();

        sut.initStudentsCourse(studentCourse, student);

        assertEquals(id, studentCourse.getStudentId());
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.getHour(), studentCourse.getStartDate().getHour());
        assertEquals(now.plusMonths(3).getHour(), studentCourse.getAssuredFinishDate().getHour());
    }

    @Test
    void registerStudent_registerStudentItself() {
        // Arrange
        Student student = new Student();

        // Act
        sut.registerStudent(student);

        // Assert
        verify(repository).insertStudent(student);
    }

    @Test
    void registerStudentandCourse_registerStudentAndCourseAndReturnData() {
        // Arrange
        Student student = new Student();
        student.setNumericId(99);
        StudentCourse course = new StudentCourse();
        List<StudentCourse> courseList = List.of(course);

        StudentDetail input = new StudentDetail();
        input.setStudent(student);
        input.setStudentCourseList(courseList);

        // Act
        StudentDetail actual = sut.registerStudentandCourse(input);

        // Assert
        verify(repository).insertStudent(student);
        verify(repository).insertStudentCourse(course);
        assertThat(actual.getStudent()).isEqualTo(student);
        assertThat(actual.getStudentCourseList()).isEqualTo(courseList);
    }

    @Test
    void updateStudent_updateStudentAndCourse() {
        // Arrange
        Student student = new Student();
        StudentCourse course1 = new StudentCourse();
        course1.setId(1);  // ID が null でないと更新対象
        StudentCourse course2 = new StudentCourse();
        course2.setId(null);  // 無視される

        List<StudentCourse> courseList = List.of(course1, course2);
        StudentDetail input = new StudentDetail();
        input.setStudent(student);
        input.setStudentCourseList(courseList);

        // Act
        sut.updateStudent(input);

        // Assert
        verify(repository, times(1)).updateStudent(student);
        verify(repository, times(1)).updateStudentCourse(course1);
        verify(repository, never()).updateStudentCourse(course2);
    }

    @Test
    void findStudentById_findAndReturnData() {
        // Arrange
        Student student = new Student();
        when(repository.findById(1)).thenReturn(Optional.of(student));

        // Act
        Student actual = sut.findStudentById(1);

        // Assert
        assertThat(actual).isEqualTo(student);
    }

    @Test
    void findStudentById_couldNotFindAndreturnNull() {
        // Arrange
        when(repository.findById(2)).thenReturn(Optional.empty());

        // Act
        Student actual = sut.findStudentById(2);

        // Assert
        assertThat(actual).isNull();
    }
}
