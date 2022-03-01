package revature.ProjectManagementAPI.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.ProjectManagementApiApplication;
import revature.ProjectManagementAPI.models.AuthenticationProvider;
import revature.ProjectManagementAPI.models.User;
import revature.ProjectManagementAPI.service.MasterService;
import revature.ProjectManagementAPI.service.TeamMemberService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    static final Logger logger = LoggerFactory.getLogger(ProjectManagementApiApplication.class);
    @Autowired
    private MasterService masterService;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Called by spring security upon successful authentication
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication successful.");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        logger.info("User's email: " + email);
        User user = userRepository.getUserByEmail(email);
        if (user == null)
        {
            //no user found -> register as new team member -> assuming we already have
            teamMemberService.createNewTeamMemberAfterOAuthSuccess(user.getEmail(), user.getName(), AuthenticationProvider.GOOGLE);
        }else{
            if (user.getRoles().getRole() == "Project Manager") {
                logger.info("Project Manager Authenticated");
                //update existing
                masterService.updateProjectManagerAfterOAuthSuccess(user, user.getName(), AuthenticationProvider.GOOGLE);
            }
            else{
                logger.info("Team Member Authenticated");
                //update existing
                teamMemberService.updateProjectManagerAfterOAuthSuccess(user, user.getName(), AuthenticationProvider.GOOGLE);
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
