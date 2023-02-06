package com.melita.ordermanagement.api;

import com.melita.ordermanagement.base.exceptions.BusinessException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApprovalRestControllerIntegrationTest {

    private final static String BASE_REQUEST_URL = "/api/v1/ordermanagement/approval";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "user",
                  roles = {"AGENT"})
    @Test
    public void approveOrderResponseConflict() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/approveOrder/0").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isConflict());
    }

    @WithMockUser(value = "user",
                  roles = {"ANT"})
    @Test
    public void approveOrderSecurityForbidden() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/approveOrder/0").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isForbidden());
    }

    @Test
    public void approveOrderSecurityUnauthorized() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/approveOrder/0").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

}