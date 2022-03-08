package revature.ProjectManagementAPI;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revature.ProjectManagementAPI.DAO.*;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.MasterService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static revature.ProjectManagementAPI.models.AuthenticationProvider.GOOGLE;
import static revature.ProjectManagementAPI.models.AuthenticationProvider.LOCAL;

@ExtendWith(MockitoExtension.class)
public class ServiceMasterTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AssignRepository assignRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskProgressRepository taskProgressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MasterService masterServices;

    @InjectMocks
    private MasterService masterService;

    @BeforeEach
    public void initBeforeTest(){
        projectRepository = mock(ProjectRepository.class);
        assignRepository = mock(AssignRepository.class);
        meetingRepository = mock(MeetingRepository.class);
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        taskProgressRepository = mock(TaskProgressRepository.class);
        masterServices = mock(MasterService.class);
        masterService = new MasterService();
        masterService.setProjectRepository(projectRepository);
        masterService.setAssignRepository(assignRepository);
        masterService.setMeetingRepository(meetingRepository);
        masterService.setTaskRepository(taskRepository);
        masterService.setTaskProgressRepository(taskProgressRepository);
        masterService.setUserRepository(userRepository);
    }

    private final Project project = new Project(1,"test", 1, "test",
            "test", "test");


    private final AssignProject assignProject = new AssignProject(1,1,"PM",
            1,"test");

    private final Meeting meeting = new Meeting(1, 1, new Timestamp(System.currentTimeMillis()), 1, "meeting", 1.5, "NONE");

    private final Task task = new Task(1,"test","test","test","test",
            1,1);

    @Test
    void shouldReturnAllProject() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());
        List<Project> projects = masterService.getAllProject();
        assertTrue(projects.isEmpty());
    }

    @Test
    void shouldReturnAllAssignedProject() {
        when(assignRepository.findAll()).thenReturn(Collections.emptyList());
        List<AssignProject> assignProjects = masterService.getAll();
        assertTrue(assignProjects.isEmpty());
    }

    @Test
    void shouldReturnAllMeetings() {
        when(meetingRepository.findAll()).thenReturn(Collections.emptyList());
        List<Meeting> meetings = masterService.getAllMeeting();
        assertTrue(meetings.isEmpty());
    }

    @Test
    void shouldReturnAllTaskProgress() {
        when(taskProgressRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskProgress> taskProgresses = masterService.getAllProgress();
        assertTrue(taskProgresses.isEmpty());
    }

    @Test
    void shouldReturnAllTask() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        List<Task> tasks = masterService.getAllTask();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnSavedProject() {
        given(projectRepository.save(project)).willReturn(project);
        Project savedProject = masterService.newProject(project);
        assertThat(savedProject).isNotNull();
    }

    @Test
    void shouldReturnSavedAssignProject() {
        given(assignRepository.save(assignProject)).willReturn(assignProject);
        AssignProject savedAssignProject = masterService.save(assignProject);
        assertThat(savedAssignProject).isNotNull();
    }

    @Test
    void shouldReturnNewMeeting() {
        given(meetingRepository.save(meeting)).willReturn(meeting);
        Meeting savedMeeting = masterService.createMeeting(meeting);
        assertThat(savedMeeting).isNotNull();
    }

    @Test
    void shouldReturnNewTask() {
        given(taskRepository.save(task)).willReturn(task);
        Task savedTask = masterService.createTask(task);
        assertThat(savedTask).isNotNull();
    }

    @Test
    void shouldDeleteById() {
        Integer userId = 1;
        willDoNothing().given(assignRepository).deleteAssignProjectByAssignUserId(userId);
        masterService.removeById(userId);
        verify(assignRepository, times(1)).deleteAssignProjectByAssignUserId(userId);
    }

//    @Test
//    void shouldUpdateManagerAfterOathSuccess() {
//        User user = new User(1,"test","test","test",
//                project,new Role(1,"member"),LOCAL);
//        String name = "confirm";
//
//        doNothing().when(masterServices).updateProjectManagerAfterOAuthSuccess(user,name,GOOGLE);
//        user.setName(name);
//        user.setAuthProvider(GOOGLE);
//        userRepository.save(user);
//
//        verify(masterServices, times(1)).updateProjectManagerAfterOAuthSuccess(user,name,GOOGLE);
//    }
}
