package raisetech.student.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        // リクエストの加工処理、入力チェックとか
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
        return "studentList";
    }

    @GetMapping("/studentsCourseList")
    public List<StudentsCourses> getStudentsCourseList() {
        return service.searchStudentsCoursesList();
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @GetMapping("/updateStudent/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // 生徒情報の取得
        Student student = service.findStudentById(id);
        // 登録済み情報を StudentDetail に格納
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(service.searchStudentsCoursesList()); // 必要に応じて
        // フォームで使用するオブジェクトとコース一覧をViewに渡す
        model.addAttribute("studentDetail", studentDetail);
        model.addAttribute("courseList", service.searchStudentsCoursesList());
        return "updateStudent"; // updateStudent.html を表示
    }

    @GetMapping("/studentDetail/{id}")
    public String showStudentDetail(@PathVariable Integer id, Model model) {
        Student student = service.findStudentById(id);
        if (student == null) {
            return "error"; // 存在しないIDに対するエラーページ（任意）
        }
        model.addAttribute("student", student);
        return "studentDetail";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.registerStudentandCourse(studentDetail.getStudent());
    //  コース情報も一緒に登録できるように実装する。コースは単体で良い。
        return "redirect:studentList";
    }
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }
        service.updateStudent(studentDetail.getStudent());
        return "redirect:/studentList";
    }
}
