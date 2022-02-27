package revature.ProjectManagementAPI.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "meeting_type", indexes = {
        @Index(name = "meeting_type_meeting_type_key", columnList = "meeting_type", unique = true)
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "meeting_type", nullable = false, length = 20)
    private String meetingType;

    @OneToMany(mappedBy = "meetingType")
    private Set<Meeting> meetings = new LinkedHashSet<>();

}