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
import raisetech.student.management.data.StudentCourses;
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
        List<StudentCourses> studentCourses = service.searchStudentCoursesList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
        return "studentList";
    }

    @GetMapping("/Student/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // 生徒情報の取得
        StudentDetail studentDetail = service.findStudent(id);
        if (studentDetail == null || studentDetail.getStudent() == null) {
            return "error"; // IDに該当する生徒が存在しない
        }
        // 登録済み情報を StudentDetail に格納
        Student student = studentDetail.getStudent();
        System.out.println("▼ 確認ログ ▼");
        System.out.println("student = " + student);// studentがnullでないか
        System.out.println("student.getName() = " + student.getName());
        System.out.println("student.getKanaName() = " + student.getKanaName());
        System.out.println("student.getMailAddress() = " + student.getMailAddress());
        model.addAttribute("studentDetail", studentDetail);
        model.addAttribute("courseList", service.searchStudentCoursesList());
        return "updateStudent";
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    // @GetMapping("/studentDetail/{id}")
    // public String showStudentDetail(@PathVariable Integer id, Model model) {
    //    Student student = service.findStudentById(id);
    //    if (student == null) {
    //        return "error"; // 存在しないIDに対するエラーページ（任意）
    //    }
    //    model.addAttribute("student", student);
    //    return "studentDetail";
    //}

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
            return "studentDetail";
        }
        service.updateStudent(studentDetail);
        return "redirect:/studentList";
    }
}
