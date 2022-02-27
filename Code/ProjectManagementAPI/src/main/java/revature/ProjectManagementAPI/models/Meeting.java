package revature.ProjectManagementAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "meeting_date", length = 10)
    private String meetingDate;

    @Column(name = "meeting_time", length = 8)
    private String meetingTime;

    @Column(name = "meeting_type")
    private Integer meetingType;

}