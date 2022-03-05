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
import revature.ProjectManagementAPI.models.User;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

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
    public void processOAuthPostLogin(String email) throws OAuth2AuthenticationException {
        User user = userRepository.getUserByEmail(email);
        if (user == null){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setAuthProvider(AuthenticationProvider.GOOGLE);
            userRepository.save(newUser);
            LOGGER.info("Created new user with email: " + email);
        }
    }
}
