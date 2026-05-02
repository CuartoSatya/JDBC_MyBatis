package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentCoursesSearchResult {
    private Integer studentId;
    private String name;
    private String kanaName;
    private String nickname;
    private String mailAddress;
    private String address;
    private Integer age;
    private String sex;
    private Boolean deleted;
    private String remark;
    private String courseName;
    private Integer statusId;
    private String statusLabel;
}
