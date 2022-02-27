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

@RestController
@RequestMapping("team")
public class TeamMembersController {

    private TeamMemberService teamMemberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMembersController.class);

    @Autowired
    public TeamMembersController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(value = "/viewmeeting/{id}")
    public ResponseEntity<List<Meeting>> getAllMeetingById(@PathVariable(value = "id") Integer projectId) {
        LOGGER.info("Team Member is viewing all meeting by project Id.");
        return new ResponseEntity<>(teamMemberService.getAllById(projectId), HttpStatus.OK);
    }

    @GetMapping(value = "/viewtask/{userid}")
    public ResponseEntity<List<Task>> getAllTaskById(@PathVariable(value = "userid") Integer userId) {
        LOGGER.info("Team Member is viewing their task by their id.");
        return new ResponseEntity<>(teamMemberService.getAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/viewtaskprogress/{projectid}")
    public ResponseEntity<List<TaskProgress>> getAllProgressByProjectId(@PathVariable(value =
            "projectid") Integer projectId) {
        LOGGER.info("Team Member is viewing their task progress by project id.");
        return new ResponseEntity<>(teamMemberService.getAllByProjectId(projectId), HttpStatus.OK);
    }

    @PostMapping(value = "/progress", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskProgress createProgress(@RequestBody TaskProgress taskProgress) {
        LOGGER.info("Team Member is updating progress for their task");
        return teamMemberService.save(taskProgress);
    }

    @PutMapping(value = "/updateprogress", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProgress(@RequestBody TaskProgress updateTaskProgress) {
        LOGGER.info("Team Member is updating the task progress.");
        teamMemberService.update(updateTaskProgress);
        return  ResponseEntity.status(HttpStatus.OK).body(updateTaskProgress);
    }

}
