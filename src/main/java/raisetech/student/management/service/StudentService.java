package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;


/** 受講生情報を取り扱うサービスです。
 *  受講生情報の検索や登録、更新処理を行います。
*/
@Service
public class StudentService {

    private final StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件検索は行いません。
     *
     * @return　受講生詳細一覧（全件）
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
     * @return　受講生詳細
     */
    public StudentDetail findStudent(Integer id) {
        Student student = repository.findStudentById(id);
        List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getNumericId());
        return new StudentDetail(student,studentCourse);
    }

    public void registerStudent(Student student) {repository.insertStudent(student);}

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudentandCourse(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.insertStudent(student);
        Integer studentId = student.getNumericId();
        List<StudentCourse> courseList = studentDetail.getStudentCourseList();
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("Course list is null or empty. No course will be registered.");
        } else {
            for (StudentCourse studentsCourse : courseList) {
                // JSON の studentId を信頼せず、必ず DB発番のIDをセット
                studentsCourse.setStudentId(studentId);
                repository.insertStudentCourse(studentsCourse);
            }
        }
        // -----------------------------
        // ③ 戻り値（登録された内容を返す）
        // -----------------------------
        StudentDetail result = new StudentDetail();
        result.setStudent(student);
        result.setStudentCourseList(courseList);
        return result;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentsCourse　受講生コース情報
     * @param student　受講生
     */
    void initStudentsCourse(StudentCourse studentsCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentsCourse.setStudentId(student.getNumericId());
        studentsCourse.setStartDate(now);
        studentsCourse.setAssuredFinishDate(now.plusMonths(3));
    }

    public Student findStudentById(Integer id) {
        return repository.findById(id).orElse(null) ;
    }

    /**
     * 受講生詳細の更新を行います。受講生の情報と受講生コース情報をそれぞれ更新します。
     *
     *  @param studentDetail 受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);

        List<StudentCourse> courseList = studentDetail.getStudentCourseList();
        if (courseList != null) {
            // 必ず ID が存在するレコードだけ更新
            courseList.stream().filter(sc -> sc.getId() != null).forEach(repository::updateStudentCourse);
        }
    }
}
