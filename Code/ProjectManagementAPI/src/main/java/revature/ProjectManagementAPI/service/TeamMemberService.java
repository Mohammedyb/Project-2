package revature.ProjectManagementAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.MeetingRepository;
import revature.ProjectManagementAPI.DAO.TaskProgressRepository;
import revature.ProjectManagementAPI.DAO.TaskRepository;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;

import java.util.List;

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

    public List<Meeting> getAllById(Integer projectId) {
        return meetingRepository.getAllByProjectId(projectId);
    }

    public List<Task> getAllByUserId(Integer userId) {
        return taskRepository.getAllByUserId(userId);
    }

    public TaskProgress save(TaskProgress taskProgress) {
        return taskProgressRepository.save(taskProgress);
    }

    public List<TaskProgress> getAllByProjectId(Integer projectId) {
        return taskProgressRepository.getAllByProjectsId(projectId);
    }

    public TaskProgress update(TaskProgress updateTaskProgress) {
        TaskProgress taskProgress = taskProgressRepository.findByAssignTaskId(updateTaskProgress.getAssignTaskId());

        taskProgress.setProjectsId(updateTaskProgress.getProjectsId());
        taskProgress.setProgressStatus(updateTaskProgress.getProgressStatus());
        taskProgress.setTaskComment(updateTaskProgress.getTaskComment());
        taskProgressRepository.save(taskProgress);

        return taskProgress;
    }



}
