package raisetech.student.management.controller.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.controller.dto.RegisterStudentRequest;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.StudentException;
import raisetech.student.management.repository.StudentRepository;
import raisetech.student.management.service.StudentService;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentApiController.class)
class StudentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService mockService;

    @MockBean
    private StudentConverter mockConverter;

    @MockBean
    private StudentRepository studentRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // 1. GET /api/student/1 のテスト
    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと () throws Exception {
        Student student = new Student();
        student.setId("8");
        student.setName("Taro");
        student.setKanaName("タロウ");
        student.setSex("1");
        student.setAge(30);
        student.setDeleted(false);

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourses(Collections.emptyList());

        when(mockService.findStudent(8)).thenReturn(detail);

        mockMvc.perform(get("/api/student/detail/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.name").value("Taro"));

        verify(mockService, times(1)).findStudent(8);
    }

    // 2. GET /studentList のテスト
    @Test
    @WithMockUser
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        when(mockService.searchStudentList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/studentList"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(mockService, times(1)).searchStudentList();
    }
    @Test
    @WithMockUser
    void 受講生詳細の受講生でidに数字以外を用いた際に入力チェックに掛かること() throws Exception {
        String id = "abc";

        mockMvc.perform(get("/api/student/detail/{id}", id))
                .andExpect(status().isBadRequest());

        verify(mockService, never()).findStudent(anyInt());
    }

    // 3. バリデーションエラーテスト（直接呼び出し）
    @Test
    void 受講生詳細の受講生で性別に数字以外を用いた際に入力チェックに掛かること() {
        RegisterStudentRequest request = new RegisterStudentRequest();
        request.setId("20");
        request.setName("Shimomura Atomu");
        request.setKanaName("Simomura Atomu");
        request.setNickname("Atomu");
        request.setMailAddress("Atoms@lycos.co.jp");
        request.setAddress("Nagano");
        request.setAge(25);
        request.setSex("It is a test.");
        request.setDeleted(false);

        Set<ConstraintViolation<RegisterStudentRequest>> violations = validator.validate(request);

        for (ConstraintViolation<RegisterStudentRequest> v : violations) {
            System.out.println(v.getPropertyPath() + " : " + v.getMessage());
        }

        assertEquals(1, violations.size());
    }

    @Test
    @WithMockUser
    void StudentExceptionが発生した場合に400が返ること() throws Exception {

        when(mockService.findStudent(1))
                .thenThrow(new StudentException("エラーです"));

        mockMvc.perform(get("/api/student/detail/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("エラーです"));
    }
}
