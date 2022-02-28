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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "project_manager_id")
    private Integer projectManagerId;

    @Column(name = "project_manager", length = 20)
    private String projectManager;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "deadline", length = 10)
    private String deadline;

}