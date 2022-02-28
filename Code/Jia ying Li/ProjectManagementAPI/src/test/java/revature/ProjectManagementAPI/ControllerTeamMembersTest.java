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
import revature.ProjectManagementAPI.models.Meeting;
import revature.ProjectManagementAPI.models.Project;
import revature.ProjectManagementAPI.service.MasterService;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectManagementApiApplication.class})
public class ControllerTeamMembersTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TeamMemberService teamMemberService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetAllMeetingById() throws Exception{
        Integer id = 1;
        List<Meeting> meetingList = new ArrayList<>();

        Meeting meeting = new Meeting(1,1,"test", "test", 1);
        meetingList.add(meeting);

        given(teamMemberService.getAllById(meetingList.get(0).getProjectId())).willReturn(meetingList);

        ResultActions response = mockMvc.perform(get("/team/viewmeeting/{id}", id));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(meetingList.size())));
    }
}
