package raisetech.student.management.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.controller.dto.RegisterStudentRequest;
import raisetech.student.management.controller.dto.UpdateStatusCourseRequest;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class StudentApiController {

    private final StudentService service;

    @Autowired
    public StudentApiController(StudentService service) {
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

    // 受講生1件取得
    @Operation(summary = "Get student by ID", description = "Fetch a student's information by their ID")
    @GetMapping(value = "/student/detail/{id}", produces = "application/json")
    public ResponseEntity<StudentDetail> getStudentJson(@PathVariable @Pattern(regexp = "\\d+") String id) {
        Integer numericId = Integer.valueOf(id);
        System.out.println("getStudentJson() called with id = " + id);
        StudentDetail detail = service.findStudent(numericId);
        System.out.println("Retrieved student name = " + detail.getStudent().getName());
        return ResponseEntity.ok(detail);
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param request 受講生詳細
     * @return
     */
    @Operation(summary = "RegisterStudent", description = "To Register Student Informations")
    @PostMapping(value = "/registerStudent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<String>> registerStudent(@Valid @RequestBody RegisterStudentRequest request,
                                                        BindingResult result) {

        System.out.println("registerStudent() called");
        System.out.println("Student name: " + request.getName());

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        System.out.println("Validation passed");
        System.out.println("Calling service.registerStudent()");
        // DTO : Data transfer object → ドメイン変換
        Student student = new Student(
                request.getId(),
                request.getName(),
                request.getKanaName(),
                request.getNickname(),
                request.getMailAddress(),
                request.getAddress(),
                request.getAge(),
                request.getSex(),
                request.getDeleted(),
                request.getRemark(),
                request.getCourse()
        );

        service.registerStudent(student);
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
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("Update success");
    }

    /**
     * 受講生情報の更新を行います。キャンセルフラグの更新もここで行います(論理削除）
     *
     * @param request 受講コース所法
     * @return 実行結果
     */
    @PutMapping("/student/course/status")
    public ResponseEntity<Void> updateStatusCourse(@RequestBody UpdateStatusCourseRequest request) {

        service.updateStatusCourse(request);
        return ResponseEntity.ok().build();
    }
}
