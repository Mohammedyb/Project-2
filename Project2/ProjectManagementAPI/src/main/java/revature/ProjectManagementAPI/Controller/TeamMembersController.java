package revature.ProjectManagementAPI.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.util.List;

/**
 * TeamMembersController.java
 * Controller Class for Team Member
 * All team members will be able to view assigned meetings, view assigned task by user id
 *   create progress on task, updating progress for task,
 *   and view all task progress by project id
 */
@RestController
@RequestMapping("team")
public class TeamMembersController {

    private TeamMemberService teamMemberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMembersController.class);

    @Autowired
    public TeamMembersController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    /**
     * View meeting post by project Id
     *
     * @param projectId the project id assignee have been assigned
     * @return meeting post with corresponding project Id
     */
    @GetMapping(value = "/viewmeeting/{id}")
    public ResponseEntity<List<Meeting>> getAllMeetingById(@PathVariable(value = "id") Integer projectId) {
        LOGGER.info("Team Member is viewing all meeting by project Id.");
        return new ResponseEntity<>(teamMemberService.getAllById(projectId), HttpStatus.OK);
    }

    /**
     * View task post by user Id
     *
     * @param userId user's Id
     * @return the task Scrum Master have assigned to each user
     */
    @GetMapping(value = "/viewtask/{userid}")
    public ResponseEntity<List<Task>> getAllTaskById(@PathVariable(value = "userid") Integer userId) {
        LOGGER.info("Team Member {} is viewing their task by their id.", userId);
        return new ResponseEntity<>(teamMemberService.getAllByUserId(userId), HttpStatus.OK);
    }

    /**
     * View task progress by project Id
     *
     * @param projectId the project id assignee have been assigned
     * @return all task progress for corresponding project
     */
    @GetMapping(value = "/viewtaskprogress/{projectid}")
    public ResponseEntity<List<TaskProgress>> getAllProgressByProjectId(@PathVariable(value =
            "projectid") Integer projectId) {
        LOGGER.info("Team Member is viewing their task progress by project id.");
        return new ResponseEntity<>(teamMemberService.getAllByProjectId(projectId), HttpStatus.OK);
    }

    /**
     * Create new task progress
     *
     * @param taskProgress the user's progress on each task with comment
     * @return task progress
     */
    @PostMapping(value = "/progress", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskProgress createProgress(@RequestBody TaskProgress taskProgress) {
        LOGGER.info("Team Member is updating progress for their task");
        return teamMemberService.save(taskProgress);
    }

    /**
     * Update task progress
     *
     * @param updateTaskProgress updating progress on status and comment
     * @return the updated progress on task
     */
    @PutMapping(value = "/updateprogress", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProgress(@RequestBody TaskProgress updateTaskProgress) {
        LOGGER.info("Team Member is updating the task progress.");
        teamMemberService.update(updateTaskProgress);
        return  ResponseEntity.status(HttpStatus.OK).body(updateTaskProgress);
    }

}
