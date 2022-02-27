package revature.ProjectManagementAPI.models;

import javax.persistence.*;

@Entity
@Table(name = "meeting_type")
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "meeting_type", nullable = false, length = 20)
    private String meetingType;

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}