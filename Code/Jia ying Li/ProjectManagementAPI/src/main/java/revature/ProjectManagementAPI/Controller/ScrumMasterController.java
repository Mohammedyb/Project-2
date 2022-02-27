package revature.ProjectManagementAPI.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.MasterService;

import java.util.List;

/**
 * ScrumMasterController.java
 * Controller Class for ScrumMaster
 * It will be able to Create new project, view project, assign members to project
 *   view all assigned project, remove members from a project
 *   create meeting, view all meetings, create new task, view all task
 *   and view all task progress
 */
@RestController
@RequestMapping("master")
public class ScrumMasterController {

    private MasterService masterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrumMasterController.class);

    @Autowired
    public ScrumMasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    /**
     * Create new project
     *
     * @param project project post the manager created
     * @return new project with project name, assigned manager, manager id, project description and deadline
     */
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Project createNewProject(@RequestBody Project project) {
        return masterService.newProject(project);
    }

    /**
     * Create new meeting
     *
     * @param meeting meeting post the manager created
     * @return new meeting post with project id, meeting date, meeting time and meeting type
     */
    @PostMapping(value = "/newmeeting", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Meeting createNewMeeting(@RequestBody Meeting meeting) {
        return masterService.createMeeting(meeting);
    }

    /**
     * Create new task
     *
     * @param task task post the manager created
     * @return new task post with name of project, description, due date, due time, assigned user id and project id
     */
    @PostMapping(value = "/newtask", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Task createNewTask(@RequestBody Task task) {
        return masterService.createTask(task);
    }

    /**
     * View all project created
     *
     * @return all project posts have created
     */
    @GetMapping(value = "/view")
    public ResponseEntity<List<Project>> getAllProject() {
        LOGGER.info("Scrum Master is viewing all the projects.");
        return new ResponseEntity<>(masterService.getAllProject(), HttpStatus.OK);
    }

    /**
     * View all assigned project
     *
     * @return all assigned project posts
     */
    @GetMapping(value = "/viewassigned")
    public ResponseEntity<List<AssignProject>> getAllAssigned() {
        LOGGER.info("Scrum Master is viewing all the assigned projects.");
        return new ResponseEntity<>(masterService.getAll(), HttpStatus.OK);
    }

    /**
     * View all meeting
     *
     * @return all meeting posts
     */
    @GetMapping(value = "/viewmeeting")
    public ResponseEntity<List<Meeting>> getAllMeeting() {
        LOGGER.info("Scrum Master is viewing all meetings");
        return new ResponseEntity<>(masterService.getAllMeeting(), HttpStatus.OK);
    }

    /**
     * View all task
     *
     * @return all task posts
     */
    @GetMapping(value = "/viewtask")
    public ResponseEntity<List<Task>> getAllTask() {
        LOGGER.info("Scrum Master is view all tasks");
        return new ResponseEntity<>(masterService.getAllTask(), HttpStatus.OK);
    }

    /**
     * View all progress
     *
     * @return all progress posts
     */
    @GetMapping(value = "/viewprogress")
    public ResponseEntity<List<TaskProgress>> getAllProgress() {
        LOGGER.info("Scrum Master is view all tasks");
        return new ResponseEntity<>(masterService.getAllProgress(), HttpStatus.OK);
    }

    /**
     * Assign members to task
     * @param assignProject with project Id, project manager id, assign user id, & assign user name
     * @return post that have been created
     */
    @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AssignProject assignProject(@RequestBody AssignProject assignProject) {
        LOGGER.info("Master is assigning members");
        return masterService.save(assignProject);
    }

    /**
     * Remove members from task by user Id
     *
     * @param id the user id
     * @return string telling the user have been removed
     */
    @DeleteMapping(value = "removeassign/{id}")
    public ResponseEntity<String> removeByUserId(@PathVariable(value = "id") Integer id) {
        LOGGER.info("Master is removing a member");
        masterService.removeById(id);
        return new ResponseEntity<String>("Member with user ID " + id + " have been removed", HttpStatus.OK);
    }
}
