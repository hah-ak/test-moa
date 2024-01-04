package my.application.gateway.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.application.gateway.dto.signIn.SignIn;
import my.application.gateway.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.gateway.filter.provider.MemberAuthenticationProvider;
import my.application.gateway.services.member.MemberSecurityService;
import my.application.gateway.services.member.MemberSignInUserDetailService;
import my.application.gateway.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = MainControllers.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}), properties = {"spring.profiles.active=dev"})
class MainControllersTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager;

    @SpyBean
    MemberAuthenticationProvider memberAuthenticationProvider;

    @MockBean
    MemberSecurityService memberSecurityService;

    @MockBean
    MemberSignInUserDetailService memberSignInUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void mockUser() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    @Test
    @DisplayName("성공 케이스")
    void loginProcess() throws Exception {
        // given
        given(memberSignInUserDetailService.loadUserByUsername("asdf@asdf.asdf"))
                .willReturn(new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf","kim", passwordEncoder.encode("1234"),null)));

        SignIn signIn = new SignIn("asdf@asdf.asdf","1234");

        // when, then
        mockMvc.perform(post("/file/test").content(objectMapper.writeValueAsString(signIn)))
                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf","kim",passwordEncoder.encode("1234"),null)))))
        ;

    }

    @Test
    void cryptotest() {
        Assertions.assertEquals(passwordEncoder.encode("1234"),passwordEncoder.encode("1234"));
    }
}