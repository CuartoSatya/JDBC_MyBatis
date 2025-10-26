package raisetech.student.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@Controller
public class StudentController {

    /** 受講生サービス
    */
    private StudentService service;

    /**　コンストラクタ
    */
    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
    }

    /**
     * 　受講生一覧検索です。
     * 　全件検索を行いますので、条件指定は行いません。
     *
     * @return　受講生情報（全件）
     */
    @GetMapping("/studentList")
    public ResponseEntity<List<StudentDetail>> getStudentList() {
        List<StudentDetail> studentList = service.searchStudentList();
        return ResponseEntity.ok(studentList);
    }

    /**
     * 　受講生検索です。
     * 　IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id　受講生ID
     * @param model
     * @return　受講生
     */
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable Integer id,Model model) {
        StudentDetail studentDetail = service.findStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    @GetMapping(value = "/api/student/{id}", produces = "application/json")
    @ResponseBody
    public StudentDetail getStudentJson(@PathVariable Integer id) {
        return service.findStudent(id);
    }

    @GetMapping("/registerStudent")
    public String showRegisterStudentForm(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @PostMapping(value = "/registerStudent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail result = service.registerStudentandCourse(studentDetail);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("Update success");
    }
}
