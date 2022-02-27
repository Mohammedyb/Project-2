package revature.ProjectManagementAPI.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MeetingDTO {
    private String meetingType;
    private Integer projectID;
    private LocalDate meetingDate;
    private LocalTime meetingTime;

    public MeetingDTO() {
    }

    public MeetingDTO(String meetingType, Integer projectID, LocalDate meetingDate, LocalTime meetingTime) {
        this.meetingType = meetingType;
        this.projectID = projectID;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }
}