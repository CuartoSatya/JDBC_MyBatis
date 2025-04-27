package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.searchStudent();
    }

    public List<Student> searchStudentsInThirties() {
        List<Student> allStudents = repository.searchStudent();
        return allStudents.stream()
                .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
                .collect(Collectors.toList());
    }

    public  List<StudentsCourses> searchStudentsCoursesList() {
        return repository.searchStudentsCourses();
    }

    public List<StudentsCourses> searchJavaCourses() {
        List<StudentsCourses> allCourses = repository.searchStudentsCourses();
        return allCourses.stream()
                .filter(course -> "JavaCourse".equals(course.getName()))
                .collect(Collectors.toList());
    }
}
