package raisetech.student.management.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService mockService;

    @MockBean
    private StudentConverter mockConverter;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // 1. GET /api/student/1 のテスト
    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    void getStudent_shouldReturnStudentDetail () throws Exception {
        Student student = new Student();
        student.setId(8);
        student.setName("Taro");
        student.setKanaName("タロウ");
        student.setSex("1");
        student.setAge(30);
        student.setDeleted(false);

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourseList(Collections.emptyList());

        when(mockService.findStudent(8)).thenReturn(detail);

        mockMvc.perform(get("/api/student/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.name").value("Taro"));

        verify(mockService, times(1)).findStudent(8);
    }

    // 2. GET /studentList のテスト
    @Test
    @WithMockUser
    void getStudentList_shouldReturnEmptyList() throws Exception {
        when(mockService.searchStudentList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/studentList"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(mockService, times(1)).searchStudentList();
    }

    // 3. バリデーションエラーテスト（直接呼び出し）
    @Test
    void SearchInputErrorCaseUsingSomethingIsNotNumber() {
        Student student = new Student();
        student.setId(20);
        student.setSex("It is a test.");
        student.setName("Shimomura Atomu");
        student.setKanaName("Simomura Atomu");
        student.setNickname("Atomu");
        student.setMailAddress("Atoms@lycos.co.jp");
        student.setAddress("Nagano");
        student.setAge(25);
        student.setDeleted(false);

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        for (ConstraintViolation<Student> v : violations) {
            System.out.println(v.getPropertyPath() + " : " + v.getMessage());
        }

        assertEquals(1, violations.size());
    }
}
