package revature.ProjectManagementAPI.security.oauth;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import revature.ProjectManagementAPI.ProjectManagementApiApplication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementApiApplication.class);

    /**
     * Configures HTTPSecurity requests for specific URLs
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/oauth_login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/oauth_login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .defaultSuccessUrl("/fragments")
                .and()
                .oauth2Login()
                .loginPage("/oauth_login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                        LOGGER.info("AuthenticationSuccessHandler invoked");
                        LOGGER.info("Authentication name: " + oauthUser.getAttributes().get("name"));

                        //TODO Implement
                        oauthUserService.processOAuthPostLogin((String) oauthUser.getAttributes().get("email"));

                        response.sendRedirect("/fragments");
                    }
                })
                //.defaultSuccessUrl("/list")
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        ;
    }

    @Autowired
    private OAuth2UserService oauthUserService;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
}
