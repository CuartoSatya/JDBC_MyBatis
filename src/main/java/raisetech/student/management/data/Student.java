package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "StudentInfomationDetail")
@Getter
@Setter
public class Student {

    @NotBlank
    private Integer id;

    @NotBlank(message = "Input name is needed.")
    private String name;

    @NotBlank(message = "kanaName is needed.")
    private String kanaName;


    @Size(max = 20, message = "Nickname might have to be 20 letters or less.")
    private String nickname;

    @Email(message = "Input by correct form.")
    private String mailAddress;

    @NotBlank(message = "address is needed.")
    private String address;

    @Min(value = 10, message = "New student might become at least 10 years old.")
    @Max(value = 120, message = "New student might be less than 120 years old.")
    private Integer age;

    @Pattern(regexp = "^([123])?$", message = "Select sex from figures 1 to 3.")
    private String sex;

    @Size(max = 100, message = "Remark might have to be 100 letters or less.")
    private String remark;

    private String course;

    private boolean deleted;
}
