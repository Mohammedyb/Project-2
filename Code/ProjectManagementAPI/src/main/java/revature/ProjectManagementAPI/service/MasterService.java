package revature.ProjectManagementAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.AssignRepository;
import revature.ProjectManagementAPI.DAO.MeetingRepository;
import revature.ProjectManagementAPI.DAO.TaskProgressRepository;
import revature.ProjectManagementAPI.DAO.TaskRepository;
import revature.ProjectManagementAPI.models.AssignProject;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;

import java.util.List;

@Service
public class MasterService {

    private AssignRepository assignRepository;

    private MeetingRepository meetingRepository;

    private TaskRepository taskRepository;

    private TaskProgressRepository taskProgressRepository;

    @Autowired
    public MasterService(AssignRepository assignRepository, MeetingRepository meetingRepository,
                         TaskRepository taskRepository, TaskProgressRepository taskProgressRepository) {
        this.assignRepository = assignRepository;
        this.meetingRepository = meetingRepository;
        this.taskRepository = taskRepository;
        this.taskProgressRepository = taskProgressRepository;
    }

    public AssignProject save(AssignProject assignProject) {
        return assignRepository.save(assignProject);
    }

    public Meeting createMeeting(Meeting meeting) {
        return  meetingRepository.save(meeting);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<AssignProject> getAll() {
        return assignRepository.findAll();
    }

    public List<Meeting> getAllMeeting() { return meetingRepository.findAll(); }

    public List<Task> getAllTask() { return taskRepository.findAll(); }

    public void removeById(Integer userId) {
        assignRepository.deleteAssignProjectByAssignUserId(userId);
    }

    public List<TaskProgress> getAllProgress() { return taskProgressRepository.findAll(); }
}
