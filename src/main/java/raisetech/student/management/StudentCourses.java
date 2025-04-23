package raisetech.student.management;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

    private int id;
    private int studentId;
    private String name;
    private String startingDate;
    private String assuredFinishingDate;
}
