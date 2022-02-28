package revature.ProjectManagementAPI.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.MeetingRepository;
import revature.ProjectManagementAPI.DAO.TaskProgressRepository;
import revature.ProjectManagementAPI.DAO.TaskRepository;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;

import java.util.List;

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

    @Autowired
    public TeamMemberService(TaskRepository taskRepository, MeetingRepository meetingRepository,
                             TaskProgressRepository taskProgressRepository) {
        this.taskRepository = taskRepository;
        this.meetingRepository = meetingRepository;
        this.taskProgressRepository = taskProgressRepository;
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

    /**
     * Update task progress
     *
     * @param updateTaskProgress updating progress on status and comment
     * @return the updated progress on task
     */
    public TaskProgress update(TaskProgress updateTaskProgress) {
        TaskProgress taskProgress = taskProgressRepository.findByAssignTaskId(updateTaskProgress.getAssignTaskId());

        taskProgress.setProjectsId(updateTaskProgress.getProjectsId());
        taskProgress.setProgressStatus(updateTaskProgress.getProgressStatus());
        taskProgress.setTaskComment(updateTaskProgress.getTaskComment());
        taskProgressRepository.save(taskProgress);

        return taskProgress;
    }

}
