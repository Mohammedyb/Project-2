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
import revature.ProjectManagementAPI.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("master")
public class ScrumMasterController {

    private ProjectService projectService;

    private MasterService masterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrumMasterController.class);

    @Autowired
    public ScrumMasterController(ProjectService projectService, MasterService masterService) {
        this.projectService = projectService;
        this.masterService = masterService;
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Project createNewProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @PostMapping(value = "/newmeeting", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Meeting createNewMeeting(@RequestBody Meeting meeting) {
        return masterService.createMeeting(meeting);
    }

    @PostMapping(value = "/newtask", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Task createNewTask(@RequestBody Task task) {
        return masterService.createTask(task);
    }

    @GetMapping(value = "/view")
    public ResponseEntity<List<Project>> getAllProject() {
        LOGGER.info("Scrum Master is viewing all the projects.");
        return new ResponseEntity<>(projectService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/viewassigned")
    public ResponseEntity<List<AssignProject>> getAllAssigned() {
        LOGGER.info("Scrum Master is viewing all the assigned projects.");
        return new ResponseEntity<>(masterService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/viewmeeting")
    public ResponseEntity<List<Meeting>> getAllMeeting() {
        LOGGER.info("Scrum Master is viewing all meetings");
        return new ResponseEntity<>(masterService.getAllMeeting(), HttpStatus.OK);
    }

    @GetMapping(value = "/viewtask")
    public ResponseEntity<List<Task>> getAllTask() {
        LOGGER.info("Scrum Master is view all tasks");
        return new ResponseEntity<>(masterService.getAllTask(), HttpStatus.OK);
    }

    @GetMapping(value = "/viewprogress")
    public ResponseEntity<List<TaskProgress>> getAllProgress() {
        LOGGER.info("Scrum Master is view all tasks");
        return new ResponseEntity<>(masterService.getAllProgress(), HttpStatus.OK);
    }

    //Assigning member projects, users should get notification by email
    @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AssignProject assignProject(@RequestBody AssignProject assignProject) {
        return masterService.save(assignProject);
    }

    @DeleteMapping(value = "removeassign/{id}")
    public ResponseEntity<String> removeByUserId(@PathVariable(value = "id") Integer id) {
        LOGGER.info("Master is removing a member");
        masterService.removeById(id);
        return new ResponseEntity<String>("Member with user ID " + id + " have been removed", HttpStatus.OK);
    }
}
