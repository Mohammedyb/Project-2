package revature.ProjectManagementAPI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revature.ProjectManagementAPI.DAO.*;
import revature.ProjectManagementAPI.models.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static revature.ProjectManagementAPI.models.AuthenticationProvider.LOCAL;

@ExtendWith(MockitoExtension.class)
class RepositoryTest {

    @Mock
    private AssignRepository assignRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskProgressRepository taskProgressRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    private final AssignProject assignProject = new AssignProject(1,1,"test",
            1,"test");

    private final Meeting meeting = new Meeting(1, 1, new Timestamp(System.currentTimeMillis()), 1, "meeting",  1.5, "NONE");

    private final Project project = new Project(1,"test", 1, "test",
            "test", "test");

    private final TaskProgress taskProgress = new TaskProgress(1,1, 1, "test",
            "test");

    private final Task task = new Task(1,"test","test","test","test",
            1,1);

    private final User user = new User(1,"test","test","test",
            project,new Role(1,"member"),LOCAL);

    @Test
    void shouldReturnAllByUserId() {
        List<AssignProject> savedAssignProject = assignRepository.getAllByAssignUserId(assignProject.getAssignUserId());
        Assertions.assertThat(savedAssignProject).isNotNull();
    }

    @Test
    void shouldDeleteByUserId() {
        Integer id = 1;

        assignRepository.deleteAssignProjectByAssignUserId(id);
        List<AssignProject> savedAssignProject = assignRepository.getAllByAssignUserId(id);

        Assertions.assertThat(savedAssignProject).isEmpty();
    }

    @Test
    void shouldReturnMeetingByProjectId() {
        List<Meeting> savedMeeting = meetingRepository.getAllByProjectId(meeting.getProjectId());
        Assertions.assertThat(savedMeeting).isNotNull();
    }

    @Test
    void shouldReturnMeetingById() {
        Optional<Meeting> savedMeeting = meetingRepository.findById(meeting.getId());
        Assertions.assertThat(savedMeeting).isNotNull();
    }

    @Test
    void shouldReturnProjectById() {
        List<Project> savedProject = projectRepository.getAllById(project.getId());
        Assertions.assertThat(savedProject).isNotNull();
    }

    @Test
    void shouldReturnProgressByTaskId(){
        Optional<TaskProgress> savedProgress = taskProgressRepository.findByAssignTaskId(taskProgress.getAssignTaskId());
        Assertions.assertThat(savedProgress).isNotNull();
    }

    @Test
    void shouldReturnProgressByProjectId(){
        List<TaskProgress> savedProgress = taskProgressRepository.getAllByProjectsId(taskProgress.getProjectsId());
        Assertions.assertThat(savedProgress).isNotNull();
    }

    @Test
    void shouldReturnProgressByTaskProgressId(){
        List<TaskProgress> savedProgress = taskProgressRepository.getAllByAssignTaskId(taskProgress.getAssignTaskId());
        Assertions.assertThat(savedProgress).isNotNull();
    }

    @Test
    void shouldReturnTaskByTaskUserId(){
        List<Task> savedTask = taskRepository.getAllByUserId(task.getUserId());
        Assertions.assertThat(savedTask).isNotNull();
    }


}
