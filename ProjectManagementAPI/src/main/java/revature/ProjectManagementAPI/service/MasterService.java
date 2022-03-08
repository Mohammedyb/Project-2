package revature.ProjectManagementAPI.service;

import com.google.api.services.calendar.CalendarScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.*;
import revature.ProjectManagementAPI.models.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * MasterService.java
 * Service Class for Scrum Master
 * Methods for create new project, view project, assign members to project
 *     view all assigned project, remove members from a project
 *     create meeting, view all meetings, create new task, view all task
 *     and view all task progress
 */
@Service @Slf4j
public class MasterService {
    private ProjectRepository projectRepository;

    private AssignRepository assignRepository;

    private MeetingRepository meetingRepository;

    private TaskRepository taskRepository;

    private TaskProgressRepository taskProgressRepository;

    private EmailService emailService;

    private UserRepository userRepository;


    public MasterService() {
    }

    @Autowired
    public MasterService(ProjectRepository projectRepository, AssignRepository assignRepository, MeetingRepository meetingRepository,
                         TaskRepository taskRepository, TaskProgressRepository taskProgressRepository, EmailService emailService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.assignRepository = assignRepository;
        this.meetingRepository = meetingRepository;
        this.taskRepository = taskRepository;
        this.taskProgressRepository = taskProgressRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void setAssignRepository(AssignRepository assignRepository) {
        this.assignRepository = assignRepository;
    }

    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setTaskProgressRepository(TaskProgressRepository taskProgressRepository) {
        this.taskProgressRepository = taskProgressRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create new project
     * @param project project post the manager created
     * @return new project with project name, assigned manager, manager id, project description and deadline
     */
    public Project newProject(Project project) {

        return projectRepository.save(project);
    }


    /**
     * Get all project created
     *
     * @return all project posts have created
     */
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    /**
     * Assign members to task
     * @param assignProject with project id, project manager id, assign user id, & assign user name
     * @return post that have been created
     */
    public AssignProject save(AssignProject assignProject) {
        return assignRepository.save(assignProject);
    }

    /**
     * Create new meeting
     *
     * @param meeting meeting post the manager created
     * @return new meeting post with project id, meeting date, meeting time and meeting type
     */
    public Meeting createMeeting(Meeting meeting) {
        log.info("Creating meeting without google calendar event: {}", meeting);
        return  meetingRepository.save(meeting);
    }

    /**
     * Creates a new meeting, and additionally adds it to the relevant project calendar
     * @param meeting meeting post the manager created
     * @return new meeting post with project id, meeting date, meeting time and meeting type
    */
    public String createMeetingWithGoogle(NewMeetingDTO meeting) {
        log.info("Creating meeting with google calendar meeting: {}", meeting);
        Meeting createdMeeting = new Meeting();
        createdMeeting.setMeetingLength(meeting.getMeetingLength());

        createdMeeting.setMeetingCalendarId("primary");
        //createdMeeting.setAuthProvider("GOOGLE");
        createdMeeting.setProjectId(meeting.getProjectId());
        createdMeeting.setTimestamp(new Timestamp(meeting.getStartDate().getValue()));
        try {
            //createdMeeting.setMeetingLink(emailService.createMeeting(meeting));
            meetingRepository.save(createdMeeting);
        } catch (Exception e) {
            log.error("ERROR trying to create new google meeting - proceeding without google event: " + e.getMessage());
            createdMeeting.setMeetingLink("NONE");
            meetingRepository.save(createdMeeting);
            return "NONE";
        }
        meetingRepository.save(createdMeeting);
        return "NONE";
    }

    /**
     * Create new task
     *
     * @param task task post the manager created
     * @return new task post with name of project, description, due date, due time, assigned user id and project id
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Get all assigned project
     *
     * @return all assigned project posts
     */
    public List<AssignProject> getAll() {
        return assignRepository.findAll();
    }

    /**
     * Get all meeting
     *
     * @return all meeting posts
     */
    public List<Meeting> getAllMeeting() { return meetingRepository.findAll(); }

    /**
     * View all task
     *
     * @return all task posts
     */
    public List<Task> getAllTask() { return taskRepository.findAll(); }

    /**
     * Remove member from task by user Id
     *
     * @param userId the user id
     */
    public void removeById(Integer userId) {
        assignRepository.deleteAssignProjectByAssignUserId(userId);
    }

    /**
     * View all progress
     *
     * @return all progress posts
     */
    public List<TaskProgress> getAllProgress() { return taskProgressRepository.findAll(); }

    public void updateProjectManagerAfterOAuthSuccess(User user, String name, AuthenticationProvider google) {
        user.setName(name);
        user.setAuthProvider(google);
        userRepository.save(user);
    }

}
