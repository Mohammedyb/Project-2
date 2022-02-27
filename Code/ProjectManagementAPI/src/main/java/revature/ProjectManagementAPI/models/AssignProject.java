package revature.ProjectManagementAPI.models;

import javax.persistence.*;

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
    private Integer projectManager;

    @Column(name = "assign_user_id")
    private Integer assignUserId;

    @Lob
    @Column(name = "assign_user_name")
    private String assignUserName;

    public String getAssignUserName() {
        return assignUserName;
    }

    public void setAssignUserName(String assignUserName) {
        this.assignUserName = assignUserName;
    }

    public Integer getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Integer assignUserId) {
        this.assignUserId = assignUserId;
    }

    public Integer getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Integer projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(Integer projectsId) {
        this.projectsId = projectsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AssignProject{" +
                "id=" + id +
                ", projectsId=" + projectsId +
                ", projectManager=" + projectManager +
                ", assignUserId=" + assignUserId +
                ", assignUserName='" + assignUserName + '\'' +
                '}';
    }
}