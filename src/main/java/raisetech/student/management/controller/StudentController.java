package raisetech.student.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.service.StudentService;
import java.util.List;

@RestController
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/studentList")
    public List<Student> getStudentList() {
    // リクエストの加工処理、入力チェックとか
        return service.searchStudentList();
    }
    @GetMapping("/studentList/thirties")
    public List<Student> getStudentsInThirties() {
        return service.searchStudentsInThirties();
    }

    @GetMapping("/studentsCoursesList")
    public List<StudentsCourses> getStudentsCoursesList() {
        return service.searchStudentsCoursesList();
    }

    @GetMapping("/studentsCoursesList/java")
    public List<StudentsCourses> getJavaCourses() {
        return service.searchJavaCourses();
    }
}
