package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@RestController
@RequestMapping("/api")
@Validated
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
    @Operation(summary = "SearchInTheStudntList", description = "ToSearchInTheStudentList")
    @GetMapping("/studentList")
    public ResponseEntity<List<StudentDetail>> getStudentList() {
        System.out.println("getStudentList() called");
        List<StudentDetail> studentList = service.searchStudentList();
        System.out.println("Returned studentList size: " + studentList.size());
        return ResponseEntity.ok(studentList);
    }

    /**
     * 　受講生検索です。
     * 　IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id　受講生ID
     * @param model
     * @return 受講生
     */
    @GetMapping("/student/html/{id}")
    public String getStudent(@PathVariable Integer id,Model model) {
        StudentDetail studentDetail = service.findStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    @Operation(summary = "Get student by ID", description = "Fetch a student’s information by their ID")
    @GetMapping(value = "/student/{id}", produces = "application/json")
    public ResponseEntity<StudentDetail> getStudentJson(@PathVariable("id") @Min(1) @Max(100) Integer id) {
        System.out.println("getStudentJson() called with id = " + id);
        StudentDetail detail = service.findStudent(id);
        System.out.println("Retrieved student name = " + detail.getStudent().getName());
        return ResponseEntity.ok(detail);
//        return service.findStudent(id);
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail 受講生詳細
     * @return
     */
    @Operation(summary = "RegisterStudent", description = "To　Resister　StudentInfomations")
    @PostMapping(value = "/registerStudent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<String>> registerStudent(
            @Validated StudentDetail studentDetail, BindingResult result) {

        System.out.println("registerStudent() called");
        System.out.println("Student name: " + studentDetail.getStudent().getName());

        if (result.hasErrors()) {
            System.out.println("Validation errors:");

            result.getFieldErrors().forEach(e ->
                    System.out.println(" - " + e.getField() + ": " + e.getDefaultMessage())
            );

            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        System.out.println("Validation passed");
        System.out.println("Calling service.registerStudentandCourse()");

        StudentDetail saved = service.registerStudentandCourse(studentDetail);

        System.out.println("Saved student ID: " + saved.getStudent().getId());
        return ResponseEntity.ok(List.of("Registration success"));
    }

    /**
     * 受講生情報の更新を行います。キャンセルフラグの更新もここで行います(論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "Update student", description = "Update student and course information")
    @PutMapping(value = "/updateStudent", consumes = "application/json")
    public ResponseEntity<String> updateStudent(@Validated StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("Update success");
    }
}
