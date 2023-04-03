package com.momo2x.dungeon.integration.communication.controller.in;

import com.momo2x.dungeon.communication.model.UserMapperImpl;
import com.momo2x.dungeon.integration.communication.controller.BaseIntegrationTestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {UserMapperImpl.class})
class UserControllerTest extends BaseIntegrationTestController {

    private static final UserDetails MOCKED_USER_DETAILS = new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return USERNAME;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };

    private static final String USER_OUTPUT_CONTENT = """
                {
                    "id": %s
                }
            """.formatted(USERNAME);

    @BeforeEach
    void mockService() {
        when(userService.getLoggedUser()).thenReturn(MOCKED_USER_DETAILS);
    }

    @Test
    void getCurrent() throws Exception {
        mockMvc.perform(
                        get("/v1/users/current")
                                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE),
                        content().json(USER_OUTPUT_CONTENT));

    }
}