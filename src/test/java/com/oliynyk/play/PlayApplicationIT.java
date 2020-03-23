package com.oliynyk.play;

import com.oliynyk.play.db.AbstractPostgreSQLContainerAwareTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PlayApplicationIT extends AbstractPostgreSQLContainerAwareTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void applicationShouldBeHealthy() {
        mockMvc.perform(
                get("/actuator/health")
        ).andExpect(
                status().isOk()
        );
    }
}