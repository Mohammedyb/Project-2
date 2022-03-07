package revature.ProjectManagementAPI;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@WithMockUser
@SpringBootTest(classes = {ProjectManagementApiApplication.class})
public class ControllerLoginsTest {
    @Autowired
    WebApplicationContext context;

    WebClient webClient;

    @Before
    public void init() throws Exception{
        webClient = new WebClient();
    }

    @After
    public void closer() throws Exception {
        webClient.close();
    }

    @Test
    public void shouldReturnOathLoginHeader() throws IOException {
        HtmlPage page = webClient.getPage("http://localhost:8080/oauth_login");
        Assert.assertEquals("Sign in - Google Accounts",page.getTitleText());
    }

    @Test
    public void shouldReturnLogOutHeader() throws IOException {
        HtmlPage page = webClient.getPage("http://localhost:8080/logout");
        Assert.assertEquals("Confirm Log Out?",page.getTitleText());
    }

    @Test
    public void shouldRegisterHeader() throws IOException {
        HtmlPage page = webClient.getPage("http://localhost:8080/Register");
        Assert.assertEquals("Sign in - Google Accounts",page.getTitleText());
    }

}
