package revature.ProjectManagementAPI.DTO;

import lombok.Getter;
import lombok.Setter;
import revature.ProjectManagementAPI.Models.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TaskDTO {
    private String taskName;
    private String description;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private String assignedUser;
    private Integer projectID;

    public TaskDTO() {
    }

    public TaskDTO(String taskName, String description, LocalDate dueDate, LocalTime dueTime, String assignedUser, Integer projectID) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.assignedUser = assignedUser;
        this.projectID = projectID;
    }
}
