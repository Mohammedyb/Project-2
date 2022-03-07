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
@Table(name = "assign_project")
public class AssignProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "projects_id")
    private Integer projectsId;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "assign_user_id")
    private Integer assignUserId;

    @Column(name = "assign_user_name")
    private String assignUserName;

}