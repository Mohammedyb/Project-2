package revature.ProjectManagementAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import revature.ProjectManagementAPI.DAO.TaskProgressRepository;
import revature.ProjectManagementAPI.models.AssignProject;
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectManagementApiApplication.class})
@AutoConfigureMockMvc
public class ControllerMembersTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TeamMemberService teamMemberService;

    @Autowired
    private TaskProgressRepository taskProgressRepository;

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

        ResultActions response = mockMvc.perform(get("/viewassign/{userid}", userid));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(assignProjectList.size())));
    }

    @Test
    public void shouldGetAllMeetingById() throws Exception{
        Integer id = 1;
        List<Meeting> meetingList = new ArrayList<>();

        Meeting meeting = new Meeting(1, 1, new Timestamp(System.currentTimeMillis()), 1, "meeting", 1.5, "NONE");
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

    @Test
    public void shouldUpdateProgress() throws Exception {
        Integer taskId = 1;
        TaskProgress taskProgress = new TaskProgress(1,1,1,
                "test","test");
        TaskProgress updateTaskProgress = new TaskProgress(1,1,1,
                "confirm","confirm");

        ResultActions response = mockMvc.perform(put("/updateprogress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateTaskProgress)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.progressStatus", is(updateTaskProgress.getProgressStatus())))
                .andExpect(jsonPath("$.taskComment", is(updateTaskProgress.getTaskComment())));

    }
}
