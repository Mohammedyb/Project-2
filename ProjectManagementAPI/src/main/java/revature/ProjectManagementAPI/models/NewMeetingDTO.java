package revature.ProjectManagementAPI.models;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NewMeetingDTO {
    private Integer projectId;
    private String meetingName;
    private DateTime startDate;
    private double meetingLength;
    private String freq;
}
