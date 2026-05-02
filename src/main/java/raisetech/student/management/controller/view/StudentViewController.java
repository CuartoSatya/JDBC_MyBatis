package raisetech.student.management.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCoursesSearchResult;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentViewController {

    private final StudentService service;

    @Autowired
    public StudentViewController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生情報の更新を行います。キャンセルフラグの更新もここで行います(論理削除）
     *
     * @param model 受講生詳細
     * @return 実行結果
     */
    // 受講生詳細画面
    @GetMapping("/detail/{id}")
    public String getStudent(@PathVariable String id, Model model) {
        if (!id.matches("\\d+")) {
            throw new IllegalArgumentException("IDは数字のみです");
        }

        Integer numericId = Integer.valueOf(id);

        StudentDetail studentDetail = service.findStudent(numericId);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    /**
     * 受講コース名で受講生情報を検索します。
     *
     **/
    @GetMapping("/search")
    public String searchStudents(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "courseName", required = false) String courseName,
        Model model) {

        List<StudentCoursesSearchResult> results;

        boolean noName = (name == null || name.isBlank());
        boolean noCourseName = (courseName == null || courseName.isBlank());

        if (noName && noCourseName) {
            results = List.of();
        } else {
            results = service.searchStudents(name, courseName);
        }

        model.addAttribute("name", name);
        model.addAttribute("courseName", courseName);
        model.addAttribute("results", results);

        return "studentSearch";
    }

    /**
     * 受講コース名一覧検索画面を表示します。
     *
     **/
    @GetMapping("/course/list")
    public String showStudentCourseList(Model model) {
        List<StudentCourse> studentCourses = service.searchAllStudentCourses();
        model.addAttribute("studentCourses", studentCourses);
        return "studentCourseList";
    }
}
