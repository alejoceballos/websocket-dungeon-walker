package com.momo2x.dungeon.integration.communication.controller;

import com.momo2x.dungeon.communication.service.DungeonService;
import com.momo2x.dungeon.communication.service.PlayerService;
import com.momo2x.dungeon.communication.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;

public abstract class BaseIntegrationTestController {

    protected static final String USERNAME = "anyuser";

    protected static final String PASSWORD = "anypassword";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected DungeonService dungeonService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected PlayerService playerService;

    @MockBean
    protected UserDetailsService userDetailsService;

    @MockBean
    protected SecurityFilterChain securityFilterChain;

    @AfterEach
    void resetMocks() {
        reset(
                this.dungeonService,
                this.playerService,
                this.userService,
                this.userDetailsService,
                this.securityFilterChain);
    }

}
