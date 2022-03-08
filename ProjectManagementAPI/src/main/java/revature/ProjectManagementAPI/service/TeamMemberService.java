package revature.ProjectManagementAPI.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.*;
import revature.ProjectManagementAPI.models.*;

import java.util.List;
import java.util.Optional;

/**
 * TeamMemberService.java
 * Service Class for Team Member
 * Methods for viewing assigned meetings, view assigned task by user id
 *   create progress on task, updating progress for task,
 *   and view all task progress by project id
 */
@NoArgsConstructor
@Service
public class TeamMemberService {
    private TaskRepository taskRepository;

    private MeetingRepository meetingRepository;

    private TaskProgressRepository taskProgressRepository;

    private UserRepository userRepository;

    private AssignRepository assignRepository;

    private User activeUser;

    private ProjectRepository projectRepository;

    @Autowired
    public TeamMemberService(TaskRepository taskRepository, MeetingRepository meetingRepository,
                             TaskProgressRepository taskProgressRepository, UserRepository userRepository,
                             AssignRepository assignRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.meetingRepository = meetingRepository;
        this.taskProgressRepository = taskProgressRepository;
        this.userRepository = userRepository;
        this.assignRepository = assignRepository;
        this.projectRepository = projectRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void setTaskProgressRepository(TaskProgressRepository taskProgressRepository) {
        this.taskProgressRepository = taskProgressRepository;
    }

    public void setAssignRepository(AssignRepository assignRepository) {
        this.assignRepository = assignRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<AssignProject> getAssignByUserId(Integer userId) { return assignRepository.getAllByAssignUserId(userId); }

    public void setActiveUser(User user){
        this.activeUser = user;
    }

    public User getActiveUser(){
        return activeUser;
    }

    /**
     * View meeting post by project Id
     *
     * @param projectId the project id assignee have been assigned
     * @return meeting post with corresponding project Id
     */
    public List<Meeting> getAllById(Integer projectId) {
        return meetingRepository.getAllByProjectId(projectId);
    }

    /**
     * View task post by user Id
     *
     * @param userId user's Id
     * @return the task Scrum Master have assigned to each user
     */
    public List<Task> getAllByUserId(Integer userId) {
        return taskRepository.getAllByUserId(userId);
    }

    /**
     * Create new task progress
     *
     * @param taskProgress the user's progress on each task with comment
     * @return task progress
     */
    public TaskProgress save(TaskProgress taskProgress) {
        /*Optional<TaskProgress> savedProgress = taskProgressRepository.findByAssignTaskId(taskProgress.getAssignTaskId());
        if(savedProgress.isPresent()) {
            throw new ResourceNotFoundException("Task Progress with Assigned Task Id: "
                    + taskProgress.getAssignTaskId() + " is already existed.");
        }*/
        return taskProgressRepository.save(taskProgress);
    }

    /**
     * View task progress by project Id
     *
     * @param projectId the project id assignee have been assigned
     * @return all task progress for corresponding project
     */
    public List<TaskProgress> getAllByProjectId(Integer projectId) {
        return taskProgressRepository.getAllByProjectsId(projectId);
    }

    public Project getProjectById(Integer projId){
        return projectRepository.getById(projId);
    }

    /**
     * Update task progress
     *
     * @param updateTaskProgress updating progress on status and comment
     * @return the updated progress on task
     */
    public TaskProgress update(TaskProgress updateTaskProgress) {
        Optional<TaskProgress> taskProgress = taskProgressRepository
                .findByAssignTaskId(updateTaskProgress.getAssignTaskId());

        TaskProgress savedProgress =taskProgress.get();

        savedProgress.setProjectsId(updateTaskProgress.getProjectsId());
        savedProgress.setProgressStatus(updateTaskProgress.getProgressStatus());
        savedProgress.setTaskComment(updateTaskProgress.getTaskComment());
        taskProgressRepository.save(savedProgress);

        return savedProgress;
    }


    public void createNewTeamMemberAfterOAuthSuccess(String email, String name, AuthenticationProvider provider) {
        User user = new User();
        user.setRoles(new Role(2, "Team Member"));
        user.setEmail(email);
        user.setName(name);
        user.setAuthProvider(provider);
        userRepository.save(user);
    }


    public void updateProjectManagerAfterOAuthSuccess(User user, String name, AuthenticationProvider google) {
        user.setName(name);
        user.setAuthProvider(google);
        userRepository.save(user);
    }


}
