package com.umbrella.currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.mock.CurrencyMockData;
import com.umbrella.currencyexchange.service.CurrencyService;
import com.umbrella.currencyexchange.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {


    private static final String CURRENCY_ENDPOINT = "/api/v1/currencies";
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";
    private final String BASE_CURRENCY = "AZN";
    private final String TARGET_CURRENCY = "USD";
    private final LocalDate DATE = LocalDate.of(2013, 3, 13);
    private final String DATE_STRING = "13.03.2013";
    private final double EXPECTED_RATE = 0.78;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void getCurrencyByDateAndCurrency_ReturnsOk_WithCurrencyInfo() throws Exception {
        CurrencyInfoRequestModel currencyInfo = CurrencyMockData.getCurrencyInfoRequestModel();

        when(currencyService.getByDateAndCurrency(DATE, TARGET_CURRENCY)).thenReturn(currencyInfo);

        mockMvc.perform(get(CURRENCY_ENDPOINT + "/historical")
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .param("from", TARGET_CURRENCY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.base").value(BASE_CURRENCY))
                .andExpect(jsonPath("$.date").value(DATE_STRING))
                .andExpect(jsonPath("$.result[0].value").value(EXPECTED_RATE));
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void getCurrencyByDateAndCurrency_ReturnsOk_WithBaseCurrencyOnly() throws Exception {
        CurrencyInfoRequestModel currencyInfo = CurrencyMockData.getCurrencyInfoRequestModel();

        when(currencyService.getByDateAndCurrency(DATE, null)).thenReturn(currencyInfo);

        mockMvc.perform(get(CURRENCY_ENDPOINT + "/historical")
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.base").value(BASE_CURRENCY))
                .andExpect(jsonPath("$.date").value(DATE_STRING))
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void getCurrencyByDateAndCurrency_ReturnsBadRequest_WithInvalidDateFormat() throws Exception {
        mockMvc.perform(get(CURRENCY_ENDPOINT + "/historical")
                        .param("date", "2022/03/17"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin")
    public void getCurrencyByDateAndCurrency_ReturnsForbidden_ForNonUserRole() throws Exception {
        mockMvc.perform(get(CURRENCY_ENDPOINT + "/historical")
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAllCurrencies_withValidDateAndToken_shouldReturnOk() throws Exception {
        when(currencyService.save(DATE)).thenReturn(Constants.API_MESSAGE);

        MvcResult result = mockMvc.perform(post(CURRENCY_ENDPOINT)
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE_PREFIX + Constants.AUTH_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
    }

    @Test
    void testGetAllCurrencies_withInvalidDateAndToken_shouldReturnBadRequest() throws Exception {
        String dateString = "invalid-date-string";

        MvcResult result = mockMvc.perform(post(CURRENCY_ENDPOINT)
                        .param("date", dateString)
                        .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE_PREFIX + Constants.AUTH_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("Failed to convert"));
    }

    @Test
    void testGetAllCurrencies_withMissingToken_shouldReturnUnauthorized() throws Exception {

        MvcResult result = mockMvc.perform(post(CURRENCY_ENDPOINT)
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testDeleteCurrencyByDate_withValidTokenAndDate_shouldReturnOk() throws Exception {

        mockMvc.perform(delete(CURRENCY_ENDPOINT)
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                        .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE_PREFIX + Constants.AUTH_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andReturn();

        verify(currencyService, times(1)).deleteByDate(DATE);
    }

    @Test
    public void testDeleteCurrencyByDate_withInvalidToken_shouldReturnUnauthorized() throws Exception {

        mockMvc.perform(delete(CURRENCY_ENDPOINT)
                        .header(AUTH_HEADER_NAME, "invalidtoken")
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        verify(currencyService, never()).deleteByDate(DATE);
    }

    @Test
    public void testDeleteCurrencyByDate_withMissingToken_shouldReturnUnauthorized() throws Exception {

        mockMvc.perform(delete(CURRENCY_ENDPOINT)
                        .param("date", DATE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        verify(currencyService, never()).deleteByDate(DATE);
    }

    @Test
    public void testDeleteCurrencyByDate_withMissingDate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete(CURRENCY_ENDPOINT)
                        .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE_PREFIX + Constants.AUTH_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(currencyService, never()).deleteByDate(any());
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testGetAllDateCurrenciesByCurrency_WithValidCurrency_ReturnsOk() throws Exception {
        // Set up mock service response
        CurrencyDateRequestModel currencyDateRequestModel = CurrencyMockData.getCurrencyDateRequestModelMock();

        when(currencyService.getAllByCurrency("USD")).thenReturn(currencyDateRequestModel);

        MvcResult result = mockMvc.perform(get(CURRENCY_ENDPOINT)
                        .param("from", "USD"))
                .andExpect(status().isOk())
                .andReturn();

        CurrencyDateRequestModel response =
                objectMapper.readValue(result.getResponse().getContentAsString(), CurrencyDateRequestModel.class);

        assertEquals("AZN", response.getBase());
        assertEquals(1, response.getCurrency().size());
        assertTrue(response.getCurrency().containsKey("USD"));
        Map<String, BigDecimal> rates = response.getCurrency().get("USD");
        assertEquals(2, rates.size());
        assertTrue(rates.containsKey("15.03.2023"));
        assertEquals(BigDecimal.valueOf(1.70), rates.get("15.03.2023"));

        verify(currencyService, times(1)).getAllByCurrency("USD");
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testGetAllDateCurrenciesByCurrency_WithMissingCurrencyParam_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get(CURRENCY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}