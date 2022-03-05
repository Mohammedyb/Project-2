package revature.ProjectManagementAPI.security.oauth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/home")
    public String displayHomePage(Model model, @AuthenticationPrincipal OAuth2User principal){
        if (principal != null){
            //sets name of oauth2 user
            String name = principal.getAttribute("name");
            //adds name as an attribute to model object for user
            model.addAttribute("name", name);
        }
        //returns a view
        return "home";
    }
}
