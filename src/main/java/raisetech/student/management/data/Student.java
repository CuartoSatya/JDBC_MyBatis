package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "StudentInfomationDetail")
@Getter
@Setter   // 今は残してOK
@ToString
public class Student {

    public Student() {
    }

    public Student(
            String id,
            String name,
            String kanaName,
            String nickname,
            String mailAddress,
            String address,
            Integer age,
            String sex,
            Boolean deleted,
            String remark,
            String course
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }

        if (age == null || age < 10 || age > 120) {
            throw new IllegalArgumentException("invalid age");
        }

        this.id = id;
        this.name = name;
        this.kanaName = kanaName;
        this.nickname = nickname;
        this.mailAddress = mailAddress;
        this.address = address;
        this.age = age;
        this.sex = sex;
        this.deleted = deleted;
        this.remark = remark;
        this.course = course;
    }

    private Integer numericId;
    private String id;
    private String name;
    private String kanaName;
    private String nickname;
    private String mailAddress;
    private String address;
    private Integer age;
    private String sex;
    private String remark;
    private String course;
    private Boolean deleted;
}
