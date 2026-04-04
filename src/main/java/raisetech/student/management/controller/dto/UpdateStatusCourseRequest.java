package raisetech.student.management.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateStatusCourseRequest {

    private Integer id;
    private Integer statusId;
    private LocalDateTime startDate;
    private LocalDateTime assuredFinishDate;
}
