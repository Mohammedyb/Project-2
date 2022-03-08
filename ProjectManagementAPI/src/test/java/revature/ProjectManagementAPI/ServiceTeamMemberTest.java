package revature.ProjectManagementAPI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revature.ProjectManagementAPI.DAO.*;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static revature.ProjectManagementAPI.models.AuthenticationProvider.LOCAL;

@ExtendWith(MockitoExtension.class)
class ServiceTeamMemberTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private TaskProgressRepository taskProgressRepository;

    @Mock
    private AssignRepository assignRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamMemberService teamMemberService;

    @BeforeEach
    public void initBeforeTest(){
        meetingRepository = mock(MeetingRepository.class);
        taskRepository = mock(TaskRepository.class);
        taskProgressRepository = mock(TaskProgressRepository.class);
        assignRepository = mock(AssignRepository.class);
        userRepository = mock(UserRepository.class);
        teamMemberService = new TeamMemberService();
        teamMemberService.setMeetingRepository(meetingRepository);
        teamMemberService.setTaskRepository(taskRepository);
        teamMemberService.setTaskProgressRepository(taskProgressRepository);
        teamMemberService.setAssignRepository(assignRepository);
    }

    private final TaskProgress taskProgress = new TaskProgress(1,1, 1, "test",
            "test");


    private final Meeting meeting = new Meeting(1, 1, new Timestamp(System.currentTimeMillis()), 1, "meeting", 1.5, "NONE");

    private final Task task = new Task(1,"test","test","test","test",
            1,1);

    private final AssignProject assignProject = new AssignProject(1,1,"test",1,
            "string");

    private final Project project = new Project(1,"test", 1, "test",
            "test", "test");

    @Test
    void shouldReturnProjectById() {
        when(meetingRepository.getAllByProjectId(meeting.getProjectId())).thenReturn(Collections.emptyList());
        List<Meeting> meetings = teamMemberService.getAllById(meeting.getProjectId());
        assertTrue(meetings.isEmpty());
    }

    @Test
    void shouldReturnTaskById() {
        when(taskRepository.getAllByUserId(task.getUserId())).thenReturn(Collections.emptyList());
        List<Task> tasks = teamMemberService.getAllByUserId(task.getUserId());
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnProgressById() {
        when(taskProgressRepository.getAllByProjectsId(taskProgress.getProjectsId())).thenReturn(Collections.emptyList());
        List<TaskProgress> taskProgresses = teamMemberService.getAllByProjectId(taskProgress.getProjectsId());
        assertTrue(taskProgresses.isEmpty());
    }

    @Test
    void shouldReturnSavedTaskProgress() {
        given(taskProgressRepository.save(taskProgress)).willReturn(taskProgress);
        TaskProgress savedTaskProgress = teamMemberService.save(taskProgress);
        assertThat(savedTaskProgress).isNotNull();
    }

    @Test
    void shouldReturnAssignById() {
        given(assignRepository.getAllByAssignUserId(assignProject.getAssignUserId())).willReturn(Collections.emptyList());
        List<AssignProject> assignProjects = teamMemberService.getAssignByUserId(assignProject.getAssignUserId());
        assertThat(assignProjects).isNotNull();
    }

    @Test
    void shouldUpdateProgress() {
        Integer taskId = 1;
        TaskProgress taskProgress = new TaskProgress(1,1,1,
                "test","test");
        TaskProgress updateTaskProgress = new TaskProgress(1,1,1,
                "confirm","confirm");

        given(taskProgressRepository.save(taskProgress)).willReturn(taskProgress);

        taskProgress.setProgressStatus(updateTaskProgress.getProgressStatus());
        taskProgress.setTaskComment(updateTaskProgress.getTaskComment());

        taskProgressRepository.save(taskProgress);

        assertThat(taskProgress.getProgressStatus()).isEqualTo("confirm");
        assertThat(taskProgress.getTaskComment()).isEqualTo("confirm");
    }
}