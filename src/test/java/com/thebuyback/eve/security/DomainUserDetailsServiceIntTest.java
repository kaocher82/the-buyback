package com.thebuyback.eve.security;

import java.util.Locale;

import com.thebuyback.eve.App;
import com.thebuyback.eve.domain.User;
import com.thebuyback.eve.repository.UserRepository;
import com.thebuyback.eve.web.rest.EnvironmentTestConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for DomainUserDetailsService.
 *
 * @see DomainUserDetailsService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ContextConfiguration(initializers = EnvironmentTestConfiguration.class)
public class DomainUserDetailsServiceIntTest {

    private static final String USER_ONE_LOGIN = "test-user-one";
    private static final String USER_TWO_LOGIN = "test-user-two";
    private static final String USER_THREE_LOGIN = "test-user-three";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService domainUserDetailsService;

    private User userOne;
    private User userTwo;
    private User userThree;

    @Before
    public void init() {
        userRepository.deleteAll();

        userOne = new User();
        userOne.setLogin(USER_ONE_LOGIN);
        userOne.setActivated(true);
        userRepository.save(userOne);

        userTwo = new User();
        userTwo.setLogin(USER_TWO_LOGIN);
        userTwo.setActivated(true);
        userRepository.save(userTwo);

        userThree = new User();
        userThree.setLogin(USER_THREE_LOGIN);
        userThree.setActivated(false);
        userRepository.save(userThree);
    }

    @Test
    public void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    public void assertThatUserCanBeFoundByLoginIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test(expected = UserNotActivatedException.class)
    public void assertThatUserNotActivatedExceptionIsThrownForNotActivatedUsers() {
        domainUserDetailsService.loadUserByUsername(USER_THREE_LOGIN);
    }

}
