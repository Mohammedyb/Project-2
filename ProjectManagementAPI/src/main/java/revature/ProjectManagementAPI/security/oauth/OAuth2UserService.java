package revature.ProjectManagementAPI.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.Controller.ScrumMasterController;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.ProjectManagementApiApplication;
import revature.ProjectManagementAPI.models.AuthenticationProvider;
import revature.ProjectManagementAPI.models.Role;
import revature.ProjectManagementAPI.models.User;
import revature.ProjectManagementAPI.service.MasterService;
import revature.ProjectManagementAPI.service.TeamMemberService;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MasterService masterService;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementApiApplication.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        LOGGER.info("CustomOAuth2UserService invoked");
        return new revature.ProjectManagementAPI.security.oauth.OAuth2User(user);
    }
    /**
     * Loads user when user request triggers through google login on html page
     * @param email
     * @throws OAuth2AuthenticationException
     */
    public void processOAuthPostLogin(String email, String name) throws OAuth2AuthenticationException {
        /*User user = userRepository.getUserByEmail(email);
        if (user == null){
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setAuthProvider(AuthenticationProvider.GOOGLE);
            userRepository.save(newUser);
            LOGGER.info("Created new user with email: " + email);
        }*/

        /*revature.ProjectManagementAPI.security.oauth.OAuth2User oAuth2User = (revature.ProjectManagementAPI.security.oauth.OAuth2User) authentication.getPrincipal();*/
        LOGGER.info("User's email: " + email);
        User user = userRepository.getUserByEmail(email);
        if (user == null)
        {
            //no user found -> register as new team member -> assuming we already have
            teamMemberService.createNewTeamMemberAfterOAuthSuccess(email, name, AuthenticationProvider.GOOGLE);
        }else{
            if (user.getRoles().getRole() == "Project Manager") {
                LOGGER.info("Project Manager Authenticated");
                //update existing
                masterService.updateProjectManagerAfterOAuthSuccess(user, user.getName(), AuthenticationProvider.GOOGLE);
            }
            else{
                LOGGER.info("Team Member Authenticated");
                //update existing
                teamMemberService.updateProjectManagerAfterOAuthSuccess(user, user.getName(), AuthenticationProvider.GOOGLE);
            }
        }
    }
}
