package revature.ProjectManagementAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/oauth_login")
    public String getLoginPage(){
        return "oauth_login";
    }

    @GetMapping("/logout")
    public String getLogoutPage(){
        return "logout";
    }

    @GetMapping("/")
    public String displayIndexPage(Model model){
        return "index";
    }

    @GetMapping("/register")
    public String displayRegisterPage(Model model){
        return "register";
    }

}
