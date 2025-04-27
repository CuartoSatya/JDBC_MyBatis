package raisetech.student.management;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentsCourses {

    private int id;
    private int studentId;
    private String name;
    private LocalDateTime startingDate;
    private LocalDateTime assuredFinishingDate;
}
