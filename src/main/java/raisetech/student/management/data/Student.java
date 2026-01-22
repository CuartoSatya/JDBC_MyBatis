package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "StudentInfomationDetail")
@Getter
@Setter
@ToString
public class Student {

    @NotNull
    private Integer id;

    @NotBlank(message = "Input name is needed.")
    private String name;

    @NotBlank(message = "kanaName is needed.")
    private String kanaName;

    @NotBlank
    @Size(max = 20, message = "Nickname might have to be 20 letters or less.")
    private String nickname;

    @NotBlank
    @Email(message = "Input by correct form.")
    private String mailAddress;

    @NotBlank(message = "address is needed.")
    private String address;

    @NotNull
    @Min(value = 10, message = "New student might become at least 10 years old.")
    @Max(value = 120, message = "New student might be less than 120 years old.")
    private Integer age;

    @NotBlank
    @Pattern(regexp = "^([123])?$", message = "Select sex from figures 1 to 3.")
    private String sex;

    @Size(max = 100, message = "Remark might have to be 100 letters or less.")
    private String remark;

    private String course;

    @NotNull
    private Boolean deleted;
}
