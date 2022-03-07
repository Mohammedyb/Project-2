package revature.ProjectManagementAPI;

import java.util.HashMap;
import java.util.Map;
import com.sun.javafx.collections.MappingChange;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revature.ProjectManagementAPI.DAO.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import revature.ProjectManagementAPI.models.Project;
import revature.ProjectManagementAPI.service.MasterService;

import java.util.Collections;
import java.util.List;
import java.util.function.ObjDoubleConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

//    @Test
//    void shouldReturnAttributes() {
//        String email = "email";
//        given(oAuth2User.getAttribute(email)).willReturn(email);
////        test = oAuth2Userlocal.getAttribute(email);
//        Assertions.assertEquals(oAuth2Userlocal.getAttribute(email), email);
//    }

}
