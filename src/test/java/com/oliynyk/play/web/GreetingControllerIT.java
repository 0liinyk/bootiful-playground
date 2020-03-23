package com.oliynyk.play.web;

import com.oliynyk.play.db.AbstractPostgreSQLContainerAwareTest;
import com.oliynyk.play.service.UserService;
import com.oliynyk.play.web.user.UserAddRequest;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.oliynyk.play.service.UserServiceImpl.DEFAULT_FALLBACK_MESSAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerIT extends AbstractPostgreSQLContainerAwareTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    void defaultGreetingShouldBeReturned() throws Exception {
        String userName = UUID.randomUUID().toString();
        getAndVerifyGreetingForUser(userName, DEFAULT_FALLBACK_MESSAGE);
    }

    @Test
    void personalizedGreetingShouldBeReturned() throws Exception {
        String userName = expectUserExists();
        getAndVerifyGreetingForUser(userName, userName);
    }

    private void getAndVerifyGreetingForUser(String userName, String expectedMessage) throws Exception {
        mockMvc.perform(get("/greeting/{name}", userName))
                .andExpect(status().isOk())
                .andExpect(
                        content().string(StringContains.containsString(expectedMessage))
                );
    }

    private String expectUserExists() {
        String userName = UUID.randomUUID().toString();
        UserAddRequest user = new UserAddRequest();
        user.setFirstName(userName);
        user.setLastName(UUID.randomUUID().toString());
        userService.save(user);
        return userName;
    }

}