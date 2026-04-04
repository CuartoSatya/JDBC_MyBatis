package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.controller.dto.UpdateStatusCourseRequest;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.StatusCourses;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCoursesSearchResult;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生情報の検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件検索は行いません。
     *
     * @return 受講生詳細一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.findAllStudent();
        List<StudentCourse> studentCoursesList = repository.searchStudentCourseList();
        return converter.convertStudentDetails(studentList, studentCoursesList);
    }

    /**
     * 受講生詳細の検索です。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 受講生ID
     * @return 受講生詳細
     */
    public StudentDetail findStudent(Integer id) {
        Student student = repository.findStudentById(id);
        List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getNumericId());
        return new StudentDetail(student, studentCourse);
    }

    public void registerStudent(Student student) {
        repository.insertStudent(student);
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値を設定します。
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudentandCourse(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.insertStudent(student);
        Integer studentId = student.getNumericId();

        List<StudentCourse> courseList = studentDetail.getStudentCourses();
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("Course list is null or empty. No course will be registered.");
        } else {
            for (StudentCourse studentCourse : courseList) {
                studentCourse.setStudentId(studentId);
                repository.insertStudentCourse(studentCourse);

                StatusCourses statusCourses = new StatusCourses();
                statusCourses.setIdCourse(studentCourse.getId());
                statusCourses.setStatus(0); // 仮申込
                repository.insertStatusCourse(statusCourses);
            }
        }
        StudentDetail result = new StudentDetail();
        result.setStudent(student);
        result.setStudentCourses(courseList);
        return result;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentsCourse 受講生コース情報
     * @param student 受講生
     */
    void initStudentsCourse(StudentCourse studentsCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentsCourse.setStudentId(student.getNumericId());
        studentsCourse.setStartDate(now);
        studentsCourse.setAssuredFinishDate(now.plusMonths(3));
    }

    public Student findStudentById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生の情報と受講生コース情報をそれぞれ更新します。
     *
     * @param studentDetail 受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);

        List<StudentCourse> courseList = studentDetail.getStudentCourses();
        if (courseList != null) {
            courseList.stream()
                    .filter(sc -> sc.getId() != null)
                    .forEach(sc -> {
                        repository.updateStudentCourse(sc);
                        repository.updateStatusCourse(toUpdateStatusCourseRequest(sc));
                    });
        }
    }

    /**
     * コース情報詳細の更新を行います。
     */
    public void updateStatusCourse(UpdateStatusCourseRequest request) {
        repository.updateStatusCourse(request);
    }

    /**
     * コース情報の検索を行います。
     */
    public List<StudentCoursesSearchResult> searchStudents(String name, String courseName) {
        List<StudentCoursesSearchResult> results = repository.searchStudents(name, courseName);
        for (StudentCoursesSearchResult r : results) {
            if (r.getStatusId() != null) {
                r.setStatusLabel(CourseStatus.fromValue(r.getStatusId()));
            }
        }
        return results;
    }

    /**
     * コース情報の一覧画面を表示します。
     */
    public List<StudentCourse> searchAllStudentCourses() {
        return repository.searchAllStudentCourses();
    }

    /**
     * StudentCourse を UpdateStatusCourseRequest に変換する。
     */
    private UpdateStatusCourseRequest toUpdateStatusCourseRequest(StudentCourse course) {
        UpdateStatusCourseRequest request = new UpdateStatusCourseRequest();
        request.setId(course.getId());
        request.setStatusId(course.getStatusId());
        request.setStartDate(course.getStartDate());
        request.setAssuredFinishDate(course.getAssuredFinishDate());
        return request;
    }
}
