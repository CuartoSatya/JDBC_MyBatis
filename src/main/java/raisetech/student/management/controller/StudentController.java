package raisetech.student.management.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;


import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@Validated
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
     * 　受講生詳細の一覧検索です。
     * 　全件検索を行いますので、条件指定は行いません。
     *
     * @return 受講生詳細一覧（全件）
     */
    @GetMapping("/studentList")
    public ResponseEntity<List<StudentDetail>> getStudentList() throws TestException {
        List<StudentDetail> studentList = service.searchStudentList();
        throw new TestException("Now, This API isn't available. Please use URL 'students', don't use 'studentList'.");
    }

    /**
     * 　受講生検索です。
     * 　IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id　受講生ID
     * @param model
     * @return 受講生
     */
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable Integer id,Model model) {
        StudentDetail studentDetail = service.findStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    @GetMapping(value = "/api/student/{id}", produces = "application/json")
    @ResponseBody
    public StudentDetail getStudentJson(@PathVariable("id") @Min(1) @Max(100) Integer id) {
        return service.findStudent(id);
    }

    @GetMapping("/error")
    public ResponseEntity<String> throwError() {
        // RuntimeException を意図的に投げる
        throw new RuntimeException("意図的な Runtime エラーです");
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail
     * @return
     */
    @PostMapping(value = "/registerStudent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<String>> registerStudent
        (@RequestBody @Validated StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
        }
        StudentDetail saved = service.registerStudentandCourse(studentDetail);
        return ResponseEntity.ok(List.of("Registration success"));
    }

    /**
     * 受講生情報の更新を行います。キャンセルフラグの更新もここで行います(論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PutMapping(value = "/updateStudent", consumes = "application/json")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("Update success");
    }
}
