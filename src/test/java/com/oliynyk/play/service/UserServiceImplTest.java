package com.oliynyk.play.service;

import com.oliynyk.play.dao.UserEntity;
import com.oliynyk.play.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.oliynyk.play.service.UserServiceImpl.DEFAULT_FALLBACK_MESSAGE;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;


class UserServiceImplTest {
    private UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    private UserService userService = new UserServiceImpl(mockUserRepository, new UserConverterImpl());

    @Test
    void defaultGreetingShouldBeReturnForUnknownUser() {
        String unknownUser = expectUnknownUserGreetingRequest();
        String personalizedGreeting = userService.generatePersonalizedGreeting(unknownUser);
        assertEquals(DEFAULT_FALLBACK_MESSAGE, personalizedGreeting);
    }


    @Test
    void personalizedGreetingShouldBeReturnForExistingUser() {
        String existingUserName = expectExistingUserGreetingRequest();
        String personalizedGreeting = userService.generatePersonalizedGreeting(existingUserName);
        assertNotNull(personalizedGreeting);
        assertTrue(personalizedGreeting.contains(existingUserName));
    }


    private String expectExistingUserGreetingRequest() {
        String userName = randomUUID().toString();
        Mockito.when(mockUserRepository.findByFirstName(userName))
               .thenReturn(singletonList(UserEntity.of(userName, randomUUID().toString())));
        return userName;
    }

    private static String expectUnknownUserGreetingRequest() {
        return randomUUID().toString();
    }
}