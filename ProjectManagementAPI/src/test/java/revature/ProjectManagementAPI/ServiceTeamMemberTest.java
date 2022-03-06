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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    private TeamMemberService teamMemberService;

    @BeforeEach
    public void initBeforeTest(){
        meetingRepository = mock(MeetingRepository.class);
        taskRepository = mock(TaskRepository.class);
        taskProgressRepository = mock(TaskProgressRepository.class);
        assignRepository = mock(AssignRepository.class);
        teamMemberService = new TeamMemberService();
        teamMemberService.setMeetingRepository(meetingRepository);
        teamMemberService.setTaskRepository(taskRepository);
        teamMemberService.setTaskProgressRepository(taskProgressRepository);
        teamMemberService.setAssignRepository(assignRepository);
    }

    private final TaskProgress taskProgress = new TaskProgress(1,1, 1, "test",
            "test");


    private final Meeting meeting = new Meeting(1, 1, 1, "NONE",
            1.5, "NONE", new Timestamp(System.currentTimeMillis()));

    private final Task task = new Task(1,"test","test","test","test",
            1,1);

    private final AssignProject assignProject = new AssignProject(1,1,"test",1,
            "string");

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
}