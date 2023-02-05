package com.melita.ordermanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melita.ordermanagement.model.dto.OrderDto;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderPlacementRestControllerIntegrationTest {

    private final static String BASE_REQUEST_URL = "/api/v1/ordermanagement/placement";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("user")
    @Test
    public void getAvailableProductsWithPackagesSecurityOk() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/available_products_with_packages").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    public void getAvailableProductsWithPackagesSecurityUnauthorized() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/available_products_with_packages").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "user",
                  roles = {"AGENT"})
    @Test
    public void getAvailableProductsWithPackagesSecurityForbidden() throws Exception {
        mvc.perform(get(BASE_REQUEST_URL + "/available_products_with_packages").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isForbidden());
    }

    @Test
    public void placeOrderSecurityUnauthorized() throws Exception {
        OrderDto orderDto = new OrderDto();
        mvc.perform(
                   post(BASE_REQUEST_URL + "/placeOrder")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(objectMapper.writeValueAsString(orderDto))
           )
           .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "user",
                  roles = {"AGENT"})
    @Test
    public void placeOrderSecurityForbidden() throws Exception {
        OrderDto orderDto = new OrderDto();
        mvc.perform(
                   post(BASE_REQUEST_URL + "/placeOrder")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(objectMapper.writeValueAsString(orderDto))
           )
           .andExpect(status().isForbidden());
    }


    @WithMockUser(value = "user",
                  roles = {"USER"})
    @Test
    public void placeOrderBadRequest() throws Exception {
        OrderDto orderDto = new OrderDto();
        mvc.perform(
                   post(BASE_REQUEST_URL + "/placeOrder")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(objectMapper.writeValueAsString(orderDto))
           )
           .andExpect(status().isBadRequest());
    }

    //    @WithMockUser("user")
    //    @Test
    //    public void placeOrderSecurityOk() throws Exception {
    //        OrderDto orderDto = new OrderDto();
    //        orderDto.setPackageIds(Set.of(1L, 3L, 4L));
    //        AddressDto addressDto = new AddressDto();
    //        orderDto.setAddress(addressDto);
    //        addressDto.setProvince("Balzan");
    //        addressDto.setCity("Balzan");
    //        addressDto.setStreet("Triq Il-Kbira");
    //        addressDto.setApartment("1");
    //        addressDto.setPostalCode("BZN 1259");
    //        PersonDto personDto = new PersonDto();
    //        orderDto.setPerson(personDto);
    //        personDto.setFirstName("Zaren");
    //        personDto.setLastName("Farrugia");
    //        personDto.setMobileNo("+356 2152 3875");
    //        personDto.setEmail("z.farrugia12@mail.com");
    //        personDto.setPassportNo("0981489");
    //
    //        Calendar currentTimeNow = Calendar.getInstance();
    //        currentTimeNow.add(Calendar.DAY_OF_YEAR, 2);
    //        orderDto.setPrefDateFrom(currentTimeNow.getTime());
    //        currentTimeNow.add(Calendar.DAY_OF_YEAR, 3);
    //        orderDto.setPrefDateTo(currentTimeNow.getTime());
    //
    //
    //        mvc.perform(
    //                MockMvcRequestBuilders.post(BASE_REQUEST_URL + "/placeOrder")
    //                                      .contentType(MediaType.APPLICATION_JSON)
    //                                      .content(objectMapper.writeValueAsString(orderDto))
    //        ).andExpect(status().isOk());
    //    }

}
