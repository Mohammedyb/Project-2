package revature.ProjectManagementAPI.security.oauth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class OAuth2User implements org.springframework.security.oauth2.core.user.OAuth2User {

    private org.springframework.security.oauth2.core.user.OAuth2User oAuth2User;

    public OAuth2User() {
    }

    public OAuth2User(org.springframework.security.oauth2.core.user.OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public void setoAuth2User(org.springframework.security.oauth2.core.user.OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public <A> A getAttribute(String name) {
        return org.springframework.security.oauth2.core.user.OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    /**
     * Gets the name attribute from the Google API
     * @return Google Account name
     */
    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
}
