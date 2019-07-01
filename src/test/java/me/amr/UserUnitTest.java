package me.amr;


import me.amr.model.LoginObj;
import me.amr.model.User;
import me.amr.repository.UserRepository;
import me.amr.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUnitTest {

    private static final String USER_ID = "test";
    private static final String USER_PW = "1234";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private User getUser() {
        return User.builder()
                .userId(USER_ID)
                .password(BCrypt.hashpw(USER_PW, BCrypt.gensalt()))
                .userName(USER_ID)
                .build();
    }

    /**
     * 사용자 로그인
     */
    @Test
    public void 사용자_로그인을_할_수_있다() {
        //given
        User user = getUser();
        LoginObj loginObj = LoginObj.builder()
                .userId(USER_ID)
                .userPw(USER_PW)
                .build();


        //when
        userRepository.save(user);

        //then
        User loginUser = userService.login(loginObj);

        assertNotNull(loginUser);
        assertTrue(loginUser.getToken() != null);
        assertEquals(loginUser.getUserId(), user.getUserId());
        assertEquals(loginUser.getUserName(), user.getUserName());
    }

    /**
     * 사용자 로그인 id 인증 실패
     */
    @Test(expected = RuntimeException.class)
    public void 아이디_오류시_로그인_실패() {
        //given
        User user = getUser();
        LoginObj loginObj = LoginObj.builder()
                .userId("abc")
                .userPw(USER_PW)
                .build();


        //when
        userRepository.save(user);

        //then
        User loginUser = userService.login(loginObj);

        thrown.expectMessage("user not found");

        assertTrue(loginUser == null);
    }

    /**
     * 사용자 로그인 pw 인증 실패
     */
    @Test(expected = RuntimeException.class)
    public void 비밀번호_오류시_로그인_실패() {
        //given
        User user = getUser();
        LoginObj loginObj = LoginObj.builder()
                .userId(USER_ID)
                .userPw("0000")
                .build();

        //when
        userRepository.save(user);

        //then
        User loginUser = userService.login(loginObj);

        //then
        thrown.expectMessage("password not valid");

        assertTrue(loginUser == null);
    }
}
