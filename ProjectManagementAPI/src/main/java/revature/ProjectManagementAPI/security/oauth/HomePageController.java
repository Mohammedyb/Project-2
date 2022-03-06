package revature.ProjectManagementAPI.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.ProjectManagementApiApplication;
import revature.ProjectManagementAPI.models.Task;
import revature.ProjectManagementAPI.models.TaskProgress;
import revature.ProjectManagementAPI.models.User;
import revature.ProjectManagementAPI.service.TeamMemberService;

import java.util.List;


@Controller
public class HomePageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    OAuth2UserService oauthUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementApiApplication.class);

    @GetMapping("/home")
    public String displayHomePage(Model model, @AuthenticationPrincipal OAuth2User principal){
        if (principal != null){
            OAuth2User oauthUser = principal;
            LOGGER.info("AuthenticationSuccessHandler invoked");
            LOGGER.info("Authentication name: " + oauthUser.getAttributes().get("name"));
            oauthUserService.processOAuthPostLogin(oauthUser.getEmail(),oauthUser.getName());
            //sets name of oauth2 user
            String name = principal.getName();
            //adds name as an attribute to model object for user
            model.addAttribute("name", name);
            String email = principal.getEmail();
            model.addAttribute("email", email);
            User user = userRepository.getUserByEmail(email);
            model.addAttribute("id", user.getId());
            List<Task> tasks = teamMemberService.getAllByUserId(user.getId());
            model.addAttribute("tasks", tasks);
        }
        //returns a view
        return "home";
    }
}
