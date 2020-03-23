package com.oliynyk.play.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.oliynyk.play.db.AbstractPostgreSQLContainerAwareTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@SpringBootTest
class UserRepositoryIT extends AbstractPostgreSQLContainerAwareTest {
    @Autowired
    UserRepository userRepository;

    @DataSet(value = "users.yml", cleanBefore = true)
    @Test
    void name() {
        List<UserEntity> usersByName = userRepository.findByFirstName("actuator");
        assertNotNull(usersByName);
        assertEquals(1, usersByName.size());
    }
}