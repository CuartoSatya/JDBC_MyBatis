package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;
import java.time.LocalDateTime;
import java.util.List;

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

    public  List<StudentsCourses> searchStudentsCoursesList() {
        return repository.searchStudentsCourses();
    }

    public void registerStudent(Student student) {repository.insertStudent(student); }

    public void registerStudentandCourse(Student student) {
        repository.insertStudent(student);

        if (student.getCourseName() != null && !student.getCourseName().isEmpty()) {
            StudentsCourses sc = new StudentsCourses();
            sc.setStudentId(student.getId());
            sc.setName(student.getCourseName());
            sc.setStartingDate(LocalDateTime.now());
            sc.setAssuredFinishingDate(LocalDateTime.now().plusMonths(3)); // 仮に3ヶ月後

            repository.insertStudentCourse(sc);
        }
    }
}
