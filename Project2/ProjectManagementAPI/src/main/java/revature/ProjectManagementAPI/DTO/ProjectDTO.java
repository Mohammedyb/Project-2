package revature.ProjectManagementAPI.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
    private String projectName;
    private String projectManager;

    public ProjectDTO() {
    }

    public ProjectDTO(String projectName, String projectManager) {
        this.projectName = projectName;
        this.projectManager = projectManager;
    }
}
