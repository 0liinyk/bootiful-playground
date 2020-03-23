package com.oliynyk.play.web;

import com.oliynyk.play.service.UserService;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GreetingController.class)
class GreetingControllerTest {
    private static final String DEFAULT_MESSAGE = "HELLO";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    void defaultGreetingShouldBeReturned() throws Exception  {
        String userName = expectUnknownUser();
        ResultActions resultActions = getGreeting(userName);
        verifyResponseIsOkAndContains(DEFAULT_MESSAGE, resultActions);
    }

    @Test
    public void personalizedGreetingShouldBeReturned() throws Exception {
        String userName = expectUserExists();
        ResultActions resultActions = getGreeting(userName);
        verifyResponseIsOkAndContains(userName, resultActions);
    }

    private String expectUnknownUser() {
        String userName = UUID.randomUUID().toString();
        when(userService.generatePersonalizedGreeting(userName)).thenReturn(DEFAULT_MESSAGE);
        return userName;
    }

    private ResultActions getGreeting(String userName) throws Exception {
        return mockMvc.perform(get("/greeting/{name}", userName));
    }

    private String expectUserExists() {
        String userName = UUID.randomUUID().toString();
        when(userService.generatePersonalizedGreeting(userName)).thenReturn(String.format("Hello %s", userName));
        return userName;
    }

    private void verifyResponseIsOkAndContains(String expectedGreetingMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(
                        content().string(StringContains.containsString(expectedGreetingMessage))
        );
    }
}