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
@Table(name = "task_progress")
public class TaskProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "assign_task_id")
    private Integer assignTaskId;

    @Column(name = "projects_id")
    private Integer projectsId;

    @Column(name = "progress_status", length = 12)
    private String progressStatus;

    @Column(name = "task_comment")
    private String taskComment;

}