package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourses;
import raisetech.student.management.domain.StudentDetail;
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
        return repository.findAllStudent();
    }

    public StudentDetail findStudent(Integer id) {
        Student student = repository.findStudentById(id);
        List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentCourses(studentCourses);
        return studentDetail;
    }

    public  List<StudentCourses> searchStudentCoursesList() {
        return repository.searchStudentCoursesList();
    }

    public void registerStudent(Student student) {repository.insertStudent(student); }

    public void registerStudentandCourse(Student student) {
        repository.insertStudent(student);

        if (student.getCourse() != null && !student.getCourse().isEmpty()) {
            StudentCourses sc = new StudentCourses();
            sc.setStudentId(student.getId());
            sc.setName(student.getCourse());
            sc.setStartingDate(LocalDateTime.now());
            sc.setAssuredFinishingDate(LocalDateTime.now().plusMonths(3)); // 仮に3ヶ月後

            repository.insertStudentCourse(sc);
        }
    }

    public Student findStudentById(Integer id) {
        return repository.findById(id).orElse(null) ;
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);

        List<StudentCourses> courseList = studentDetail.getStudentCourses();
        if (courseList != null) {
            for (StudentCourses sc : courseList) {
                // 必ず ID が存在するレコードだけ更新
                if (sc.getId() != null) {
                    repository.updateStudentCourse(sc);
                }
            }
        }
    }

//    @Transactional
//    public void updateStudent(Student student) {
//        repository.updateStudent(student);

//        if (student.getCourse() != null && !student.getCourse().isEmpty()) {
//            StudentCourses sc = new StudentCourses();
//            sc.setStudentId(student.getId());
//            sc.setName(student.getCourse());
//            sc.setStartingDate(LocalDateTime.now());
//            sc.setAssuredFinishingDate(LocalDateTime.now().plusMonths(3)); // 仮に3ヶ月後
//            repository.updateStudentCourse(sc);
//        }
//    }
}
