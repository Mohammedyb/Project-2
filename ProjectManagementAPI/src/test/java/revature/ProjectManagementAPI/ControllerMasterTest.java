package revature.ProjectManagementAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.MasterService;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.nanoTime;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectManagementApiApplication.class})
public class ControllerMasterTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MasterService masterService;

    @MockBean
    private TeamMemberService teamMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllProject() throws Exception {
        List<Project> projectList = new ArrayList<>();
        Project project = new Project(1,"test", 1, "test",
                "test", "test");
        projectList.add(project);

        given(masterService.getAllProject()).willReturn(projectList);
        ResultActions response = mockMvc.perform(get("/master/view"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(projectList.size())));
    }

    @Test
    public void shouldReturnAllAssignedProject() throws Exception {
        List<AssignProject> projectList = new ArrayList<>();
        AssignProject assignProject = new AssignProject(1,1,"test",
                1,"test");
        projectList.add(assignProject);

        given(masterService.getAll()).willReturn(projectList);
        ResultActions response = mockMvc.perform(get("/master/viewassigned"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(projectList.size())));
    }

    @Test
    public void shouldReturnAllMeeting() throws Exception {
        List<Meeting> meetingList = new ArrayList<>();
        Meeting meeting = new Meeting(1, 1, 1, "NONE",
                1.5, "NONE", new Timestamp(System.currentTimeMillis())/*, Arrays.asList("one", "two")*/);
        meetingList.add(meeting);

        given(masterService.getAllMeeting()).willReturn(meetingList);
        ResultActions response = mockMvc.perform(get("/master/viewmeeting"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(meetingList.size())));
    }

    @Test
    public void shouldReturnAllTask() throws Exception {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(1,"test","test","test","test",
                1,1);
        taskList.add(task);

        given(masterService.getAllTask()).willReturn(taskList);
        ResultActions response = mockMvc.perform(get("/master/viewtask"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(taskList.size())));
    }

    @Test
    public void shouldReturnAllProgress() throws Exception {
        List<TaskProgress> taskProgressList = new ArrayList<>();
        TaskProgress taskProgress = new TaskProgress(1,1,1,
                "test","test");
        taskProgressList.add(taskProgress);

        given(masterService.getAllProgress()).willReturn(taskProgressList);
        ResultActions response = mockMvc.perform(get("/master/viewprogress"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(taskProgressList.size())));
    }

    @Test
    public void shouldCreateProject() throws Exception {
        Project project = new Project(1,"test", 1, "test",
                "test", "test");
        when(masterService.newProject(any(Project.class))).thenReturn(project);

        ResultActions response = mockMvc.perform(post("/master/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(project.getId()))
                .andExpect(jsonPath("$.name").value(project.getName()))
                .andExpect(jsonPath("$.projectManagerId").value(project.getProjectManagerId()))
                .andExpect(jsonPath("$.projectManager").value(project.getProjectManager()))
                .andExpect(jsonPath("$.projectDescription").value(project.getProjectDescription()))
                .andExpect(jsonPath("$.deadline").value(project.getDeadline()));
    }

    @Test
    public void shouldCreateMeeting() throws Exception {
        Meeting meeting = new Meeting(1,1,1, "test",
                1.5, "test", new Timestamp(System.currentTimeMillis()));
        when(masterService.createMeeting(any(Meeting.class))).thenReturn(meeting);

        ResultActions response = mockMvc.perform(post("/master/newmeeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meeting)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(meeting.getId()))
                .andExpect(jsonPath("$.projectId").value(meeting.getProjectId()))
                .andExpect(jsonPath("$.meetingType").value(meeting.getMeetingType()))
                .andExpect(jsonPath("$.meetingCalendarId").value(meeting.getMeetingCalendarId()))
                .andExpect(jsonPath("$.meetingLength").value(meeting.getMeetingLength()))
                .andExpect(jsonPath("$.meetingLink").value(meeting.getMeetingLink()));
    }

    @Test
    public void shouldGetAllAssignById() throws Exception{
        Integer userid = 1;
        List<AssignProject> assignProjectList = new ArrayList<>();

        AssignProject assignProject = new AssignProject(1,1,"test",1,
                "string");
        assignProjectList.add(assignProject);

        given(teamMemberService.getAssignByUserId(assignProjectList.get(0).getAssignUserId()))
                .willReturn(assignProjectList);

        ResultActions response = mockMvc.perform(get("/viewassign/{userid}", userid));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(assignProjectList.size())));
    }

    @Test
    public void shouldGetAllMeetingById() throws Exception{
        Integer id = 1;
        List<Meeting> meetingList = new ArrayList<>();

        Meeting meeting = new Meeting(1,1,1, "test", 1.0,
                "test", new Timestamp(System.currentTimeMillis()));
        meetingList.add(meeting);

        given(teamMemberService.getAllById(meetingList.get(0).getProjectId())).willReturn(meetingList);

        ResultActions response = mockMvc.perform(get("/viewmeeting/{id}", id));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(meetingList.size())));
    }

    @Test
    public void shouldGetAllTaskById() throws Exception{
        Integer userid = 1;
        List<Task> taskList = new ArrayList<>();

        Task task = new Task(1,"test","test","test","test",
                1,1);
        taskList.add(task);

        given(teamMemberService.getAllByUserId(taskList.get(0).getUserId())).willReturn(taskList);

        ResultActions response = mockMvc.perform(get("/viewtask/{userid}", userid));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(taskList.size())));
    }

    @Test
    public void shouldGetAllProgressById() throws Exception{
        Integer projectId = 1;
        List<TaskProgress> progressList = new ArrayList<>();

        TaskProgress taskProgress = new TaskProgress(1,1,1,
                "test","test");
        progressList.add(taskProgress);

        given(teamMemberService.getAllByProjectId(progressList.get(0).getProjectsId())).willReturn(progressList);

        ResultActions response = mockMvc.perform(get("/viewtaskprogress/{projectid}", projectId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(progressList.size())));
    }
}
