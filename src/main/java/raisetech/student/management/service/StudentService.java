package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

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
     * 受講生一覧検索です。
     * 全件検索を行うので、条件検索は行いません。
     *
     * @return　受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.findAllStudent();
        List<StudentCourses> studentCoursesList = repository.searchStudentCoursesList();
        return converter.convertStudentDetails(studentList, studentCoursesList);
    }

    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 受講生ID
     * @return　受講生
     */
    public StudentDetail findStudent(Integer id) {
        Student student = repository.findStudentById(id);
        List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
        return new StudentDetail(student,studentCourses);
    }

    public void registerStudent(Student student) {repository.insertStudent(student);}

    @Transactional
    public StudentDetail registerStudentandCourse(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.insertStudent(student);

        Integer studentId = student.getId();
        List<StudentCourses> courseList = studentDetail.getStudentCourses();

        if (courseList != null && !courseList.isEmpty()) {

            for (StudentCourses sc : courseList) {

                // JSON の studentId を信頼せず、必ず DB発番のIDをセット
                sc.setStudentId(studentId);

                repository.insertStudentCourse(sc);
            }
        }
        // -----------------------------
        // ③ 戻り値（登録された内容を返す）
        // -----------------------------
        StudentDetail result = new StudentDetail();
        result.setStudent(student);
        result.setStudentCourses(courseList);

        return result;
    }

    public Student findStudentById(Integer id) {
        return repository.findById(id).orElse(null) ;
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);

        List<StudentCourses> courseList = studentDetail.getStudentCourses();
        if (courseList != null) {
            for (StudentCourses sc : courseList) {
                // 必ず ID が存在するレコードだけ更新
                if (sc.getId() != null) {
                    repository.updateStudentCourse(sc);
                }
            }
        }
    }
}
