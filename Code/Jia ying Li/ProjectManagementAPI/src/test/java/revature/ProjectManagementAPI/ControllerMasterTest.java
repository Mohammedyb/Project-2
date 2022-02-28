package revature.ProjectManagementAPI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.MasterService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllProjectt() throws Exception {
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
        Meeting meeting = new Meeting(1,1,"test", "test", 1);
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
}
