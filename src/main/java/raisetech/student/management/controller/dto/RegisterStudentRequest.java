package raisetech.student.management.controller.dto;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter

public class RegisterStudentRequest {

    @NotBlank(message = "ID is required.")
    private String id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Kana name is required.")
    private String kanaName;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Email(message = "Invalid email format.")
    private String mailAddress;

    @NotBlank
    private String address;

    @NotNull
    @Min(10)
    @Max(120)
    private Integer age;

    @NotBlank
    @Pattern(regexp = "^([123])$", message = "Sex must be 1,2,3")
    private String sex;

    @NotNull
    private Boolean deleted;

    private String remark;
    private String course;

    // getter / setter
}
