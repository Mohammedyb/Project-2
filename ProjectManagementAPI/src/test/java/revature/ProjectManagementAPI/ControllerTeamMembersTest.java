package revature.ProjectManagementAPI;

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
import revature.ProjectManagementAPI.models.AssignProject;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectManagementApiApplication.class})
public class ControllerTeamMembersTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TeamMemberService teamMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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

        ResultActions response = mockMvc.perform(get("/team//viewassign/{userid}", userid));

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

        ResultActions response = mockMvc.perform(get("/team/viewmeeting/{id}", id));

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

        ResultActions response = mockMvc.perform(get("/team/viewtask/{userid}", userid));

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

        ResultActions response = mockMvc.perform(get("/team/viewtaskprogress/{projectid}", projectId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(progressList.size())));
    }

    @Test
    public void shouldCreateProgress() throws Exception {
        TaskProgress taskProgress = new TaskProgress(1,1,1,
                "test","test");
        when(teamMemberService.save(any(TaskProgress.class))).thenReturn(taskProgress);

        ResultActions response = mockMvc.perform(post("/team/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskProgress)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskProgress.getId()))
                .andExpect(jsonPath("$.assignTaskId").value(taskProgress.getAssignTaskId()))
                .andExpect(jsonPath("$.projectsId").value(taskProgress.getProjectsId()))
                .andExpect(jsonPath("$.progressStatus").value(taskProgress.getProgressStatus()))
                .andExpect(jsonPath("$.taskComment").value(taskProgress.getTaskComment()));
    }

}
