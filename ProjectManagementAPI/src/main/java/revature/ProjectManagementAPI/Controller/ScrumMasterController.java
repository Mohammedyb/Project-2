package revature.ProjectManagementAPI.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    @Value("${api.config.api2URL:http://localhost:8081/email}")
    String url;

    @Autowired
    private final MasterService masterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrumMasterController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ScrumMasterController(MasterService masterService, RestTemplate restTemplate) {
        this.masterService = masterService;
        this.restTemplate = restTemplate;
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
     * Creates a new meeting, and additionally adds the relevant event to the project's calendar
     * @param meeting meeting post the manager created
     * @return the hyperlink to a new google calendar event (or, in the event of an error, the string "NONE"
    */
    @PostMapping(value = "/newmeeting/google", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNewMeetingWithGoogle(@RequestBody NewMeetingDTO meeting) {return masterService.createMeetingWithGoogle(meeting);}

    /**
     * Create new task
     *
     * @param task task post the manager created
     * @return new task post with name of project, description, due date, due time, assigned user id and project id
     */
    @PostMapping(value = "/newtask", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewTask(@RequestBody Task task) {
        masterService.createTask(task);

        //send email to team members assigned to new task
        ResponseEntity resp = restTemplate.postForEntity(url, task, null);

        if(resp.getStatusCode().is5xxServerError()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
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
     * @param assignProject with project id, project manager id, assign user id, & assign user names
     * @return post that have been created
     */
    @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignProject(@RequestBody AssignProject assignProject) {
        LOGGER.info("Master is assigning members");
         masterService.save(assignProject);

         //send email to team members assigned to new projects
        // params for email are to, subject and content respectively
        ResponseEntity resp = restTemplate.postForEntity(url, assignProject, null);

        if(resp.getStatusCode().is5xxServerError()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
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
        return new ResponseEntity<>("Member with user ID " + id + " have been removed", HttpStatus.OK);
    }
}
