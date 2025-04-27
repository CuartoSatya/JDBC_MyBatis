package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private Integer id;
    private String name;
    private String kanaName;
    private String nickname;
    private String mailAddress;
    private String address;
    private Integer age;
    private String sex;
    private String remark;
    private boolean isDeleted;
}
