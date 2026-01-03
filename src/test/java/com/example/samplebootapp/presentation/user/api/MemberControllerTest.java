package com.example.samplebootapp.presentation.user.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import com.example.samplebootapp.presentation.user.request.MemberUpdateRequest;

import com.example.samplebootapp.application.security.MemberUserDetails;
import com.example.samplebootapp.application.user.MemberApplicationService;
import com.example.samplebootapp.application.user.query.UserQueryService;
import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.presentation.user.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MemberControllerTest {

        private MockMvc mockMvc;
        private MemberApplicationService memberApplicationService;
        private UserQueryService userQueryService;

        @BeforeEach
        void setUp() {
                memberApplicationService = mock(MemberApplicationService.class);
                userQueryService = mock(UserQueryService.class);
                MemberController controller = new MemberController(memberApplicationService, userQueryService);
                mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                .setCustomArgumentResolvers(
                                                new org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver())
                                .build();
        }

        @Test
        @DisplayName("GET /api/users/me: 認証済みの場合は会員情報を返す")
        void testGetMeSuccess() throws Exception {
                // Mock Security Context
                Member member = new Member("user-id", "Test User", "test@example.com", "hashed-password");
                MemberUserDetails userDetails = new MemberUserDetails(member);

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userDetails);

                SecurityContext securityContext = mock(SecurityContext.class);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                SecurityContextHolder.setContext(securityContext);

                when(userQueryService.findById("user-id"))
                                .thenReturn(new UserResponse("user-id", "Test User", "test@example.com"));

                mockMvc.perform(get("/api/users/me"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("user-id"))
                                .andExpect(jsonPath("$.name").value("Test User"))
                                .andExpect(jsonPath("$.email").value("test@example.com"));

                SecurityContextHolder.clearContext();
        }

        @Test
        @DisplayName("PUT /api/users/me: 認証済みの場合は会員情報を更新できる")
        void testUpdateMeSuccess() throws Exception {
                // Mock Security Context
                Member member = new Member("user-id", "Test User", "test@example.com", "hashed-password");
                MemberUserDetails userDetails = new MemberUserDetails(member);

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userDetails);

                SecurityContext securityContext = mock(SecurityContext.class);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                SecurityContextHolder.setContext(securityContext);

                // Request
                ObjectMapper objectMapper = new ObjectMapper();
                MemberUpdateRequest request = new MemberUpdateRequest();
                request.setName("Updated Name");
                request.setEmail("updated@example.com");

                mockMvc.perform(put("/api/users/me")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk());

                verify(memberApplicationService).update("user-id", "Updated Name", "updated@example.com");

                SecurityContextHolder.clearContext();
        }

        @Test
        @DisplayName("DELETE /api/users/me: 認証済みの場合は退会できる")
        void testWithdrawMeSuccess() throws Exception {
                // Mock Security Context
                Member member = new Member("user-id", "Test User", "test@example.com", "hashed-password");
                MemberUserDetails userDetails = new MemberUserDetails(member);

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userDetails);

                SecurityContext securityContext = mock(SecurityContext.class);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                SecurityContextHolder.setContext(securityContext);

                mockMvc.perform(delete("/api/users/me"))
                                .andExpect(status().isOk());

                verify(memberApplicationService).withdraw("user-id");

                // Note: SecurityContextHolder.clearContext() is called in the controller,
                // but for this unit test with mocked SecurityContext, we just verify the
                // service call
                // and clean up our test context.
                SecurityContextHolder.clearContext();
        }
}
