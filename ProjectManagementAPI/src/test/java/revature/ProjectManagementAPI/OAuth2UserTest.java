package revature.ProjectManagementAPI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class OAuth2UserTest {

    @Mock
    private OAuth2User oAuth2User;

    @InjectMocks
    private revature.ProjectManagementAPI.security.oauth.OAuth2User oAuth2Userlocal;

    @BeforeEach
    public void initBeforeTest(){
        oAuth2User = mock(OAuth2User.class);
        oAuth2Userlocal = new revature.ProjectManagementAPI.security.oauth.OAuth2User();
        oAuth2Userlocal.setoAuth2User(oAuth2User);
    }

    @Test
    void shouldReturnName() {
        String name = "name";
        given(oAuth2User.getAttribute(name)).willReturn(name);
        String test = oAuth2Userlocal.getName();
        assertEquals(name,test);
    }

    @Test
    void shouldReturnEmail() {
        String email = "email";
        given(oAuth2User.getAttribute(email)).willReturn(email);
        String test = oAuth2Userlocal.getEmail();
        assertEquals(email,test);
    }

}
