package revature.ProjectManagementAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "description", length = 60)
    private String description;

    @Column(name = "due_date", length = 10)
    private String dueDate;

    @Column(name = "due_time", length = 8)
    private String dueTime;

    @Column(name = "assigned_user")
    private Integer userId;

    @Column(name = "project_id")
    private Integer projectsId;

}