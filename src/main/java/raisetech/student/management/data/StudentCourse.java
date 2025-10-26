package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourse {

    private Integer id;
    private Integer studentId;
    private String name;
    private LocalDateTime startingDate;
    private LocalDateTime assuredFinishingDate;
}
