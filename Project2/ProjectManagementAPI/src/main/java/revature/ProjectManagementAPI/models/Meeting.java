package revature.ProjectManagementAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "meeting_type")
    private Integer meetingType;

    @Column(name = "meeting_calendar_id")
    private String meetingCalendarId;

    @Column(name = "meeting_length")
    private double meetingLength;

    @Column(name = "meeting_link")
    private String meetingLink;
}