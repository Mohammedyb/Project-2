package revature.ProjectManagementAPI.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import revature.ProjectManagementAPI.DAO.MeetingRepository;
import revature.ProjectManagementAPI.DAO.ProjectRepository;
import revature.ProjectManagementAPI.DAO.TaskProgressRepository;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.ProjectManagementApiApplication;
import revature.ProjectManagementAPI.models.*;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomePageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    OAuth2UserService oauthUserService;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    TaskProgressRepository taskProgressRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementApiApplication.class);

    @GetMapping("/home")
    public String displayHomePage(Model model, @AuthenticationPrincipal OAuth2User principal){
        if (principal != null){
            OAuth2User oauthUser = principal;
            
            LOGGER.info("AuthenticationSuccessHandler invoked");
            LOGGER.info("Authentication name: " + oauthUser.getAttributes().get("name"));
            oauthUserService.processOAuthPostLogin(oauthUser.getEmail(),oauthUser.getName());

            User user = setupUserModel(model, principal);
            teamMemberService.setActiveUser(user);
            viewAssignedTasks(model, user);
            viewAssignedProjects(model, user);
            viewAssignedMeetings(model, user);
            TaskProgress newProgress = new TaskProgress();
            newProgress.setAssignTaskId(user.getId());
            model.addAttribute("newProgress", newProgress);
        }
        else{
            teamMemberService.setActiveUser(null);
        }
        //returns a view
        return "home";
    }

    public User setupUserModel(Model model, OAuth2User principal) {
        String name = principal.getName();
        //adds name as an attribute to model object for user
        model.addAttribute("name", name);
        String email = principal.getEmail();
        model.addAttribute("email", email);
        User user = userRepository.getUserByEmail(email);
        model.addAttribute("id", user.getId());
        model.addAttribute("user", user);
        return  user;
    }

    public void viewAssignedProjects(Model model, User user) {
        List<AssignProject> assignProjects = teamMemberService.getAssignByUserId(user.getId());
        List<Project> projectList = new ArrayList<>();
        for (AssignProject project : assignProjects)
        {
            projectList.add(projectRepository.getById(project.getProjectsId()));
        }
        model.addAttribute("projects", projectList);
    }

    public void viewAssignedTasks(Model model, User user){
        List<Task> tasks = teamMemberService.getAllByUserId(user.getId());
        model.addAttribute("tasks", tasks);
        List<TaskProgress> taskProgressList = taskProgressRepository.getAllByAssignTaskId(user.getId());
        model.addAttribute("taskProgress", taskProgressList);
    }

    public void viewAssignedMeetings(Model model, User user){
        if (teamMemberService.getAllById(user.getProjects().getId()) != null)
        {
            List<Meeting> meetings = teamMemberService.getAllById(user.getProjects().getId());
            model.addAttribute("meetings", meetings);
            List<String> meetingTypes = new ArrayList<>();
            meetingTypes.add("Daily Standup");
            meetingTypes.add("Sprint Review");
            meetingTypes.add("Sprint Planning");
            model.addAttribute("meeting_types", meetingTypes);
        }
    }
}
