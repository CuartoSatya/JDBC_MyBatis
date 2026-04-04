package raisetech.student.management.controller.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.data.Student;
import raisetech.student.management.repository.StudentRepository;
import raisetech.student.management.service.StudentService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentViewController.class)
class StudentViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @MockBean
    private StudentRepository studentRepository;

    // 正常系テスト
    @Test
    void 学生詳細画面が正しく表示されること() throws Exception {

        // ① モックデータ作成
        Student student = new Student();
        student.setId("1");
        student.setName("Taro");

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourses(Collections.emptyList());

        // ② Serviceの戻り値設定
        when(service.findStudent(1)).thenReturn(detail);

        // ③ 実行 & 検証
        mockMvc.perform(get("/student/detail/1"))
                .andExpect(status().isOk())                          // HTTPステータス
                .andExpect(view().name("updateStudent"))             // View名
                .andExpect(model().attributeExists("studentDetail")) // Modelに存在
                .andExpect(model().attribute("studentDetail", detail));
    }

    // 異常系（IDが数字じゃない）
    @Test
    void IDが数字以外の場合はエラーになること() throws Exception {

        mockMvc.perform(get("/student/detail/abc"))
                .andExpect(status().isBadRequest());
    }

@Test
    void 受講コース一覧画面が表示できること() throws Exception {
        StudentCourse course = new StudentCourse();
        course.setId(1);
        course.setStudentId(1);
        course.setCourseName("AWScourse");

        when(service.searchAllStudentCourses()).thenReturn(List.of(course));

        mockMvc.perform(get("/student/course/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentCourseList"))
                .andExpect(model().attributeExists("studentCourses"));

        verify(service, times(1)).searchAllStudentCourses();
    }
}
