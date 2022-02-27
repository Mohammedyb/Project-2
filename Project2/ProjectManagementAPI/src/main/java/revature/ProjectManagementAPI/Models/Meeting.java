package revature.ProjectManagementAPI.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "meetings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project projectID;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "meeting_time")
    private LocalTime meetingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_type")
    private MeetingType meetingType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getId().equals(meeting.getId()) && getProjectID().equals(meeting.getProjectID()) && getMeetingDate().equals(meeting.getMeetingDate()) && getMeetingTime().equals(meeting.getMeetingTime()) && getMeetingType().equals(meeting.getMeetingType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProjectID(), getMeetingDate(), getMeetingTime(), getMeetingType());
    }
}