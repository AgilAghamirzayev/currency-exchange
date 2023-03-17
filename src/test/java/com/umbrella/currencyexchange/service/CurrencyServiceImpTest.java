package com.umbrella.currencyexchange.service;

import com.umbrella.currencyexchange.client.CurrencyClient;
import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyRequestModel;
import com.umbrella.currencyexchange.entity.Currency;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import com.umbrella.currencyexchange.exception.ResourceNotFoundException;
import com.umbrella.currencyexchange.mapper.CurrencyMapper;
import com.umbrella.currencyexchange.mock.CurrencyMockData;
import com.umbrella.currencyexchange.repository.CurrencyInfoRepository;
import com.umbrella.currencyexchange.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyServiceImpTest {
    @InjectMocks
    private CurrencyServiceImp currencyService;

    @Mock
    private CurrencyInfoRepository currencyInfoRepository;

    @Mock
    private CurrencyClient currencyClient;

    @Mock
    private CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllByCurrency_WithValidInput_ReturnsMappedModel() {
        // Arrange
        String from = "USD";
        List<Currency> currencyList = CurrencyMockData.getCurrencyListMock();
        CurrencyDateRequestModel expectedModel = CurrencyMockData.getCurrencyDateRequestModelMock();

        when(currencyInfoRepository.getAllByCurrency(from)).thenReturn(currencyList);
        when(currencyMapper.mapToCurrencyDateRequestModel(currencyList, from)).thenReturn(expectedModel);

        // Act
        CurrencyDateRequestModel actualModel = currencyService.getAllByCurrency(from);

        // Assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void testGetAllByCurrency_WithEmptyResult_ReturnsEmptyModel() {
        // Arrange
        String from = "USD";
        List<Currency> currencyList = List.of();
        CurrencyDateRequestModel expectedModel = CurrencyMockData.getCurrencyDateRequestEmptyModelMock();

        when(currencyInfoRepository.getAllByCurrency(from)).thenReturn(currencyList);
        when(currencyMapper.mapToCurrencyDateRequestModel(currencyList, from)).thenReturn(expectedModel);

        // Act
        CurrencyDateRequestModel actualModel = currencyService.getAllByCurrency(from);

        // Assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void testSave_WithDateInDatabase_ReturnsDatabaseMessage() {
        // Arrange
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        Optional<CurrencyInfo> optionalCurrencyInfo = Optional.of(currencyInfo);

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(optionalCurrencyInfo);

        // Act
        String result = currencyService.save(date);

        // Assert
        assertEquals(Constants.DATABASE_MESSAGE.formatted(date), result);
    }

    @Test
    void testSave_WithDateNotInDatabase_ReturnsApiMessage() {
        // Arrange
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.empty());

        CurrencyInfoDto currencyInfoDtoMock = CurrencyMockData.getCurrencyInfoDtoMock();
        when(currencyClient.getCurrencyInfo(formatDate)).thenReturn(currencyInfoDtoMock);

        // Act
        String result = currencyService.save(date);

        // Assert
        assertEquals(Constants.API_MESSAGE.formatted(date), result);
    }

    @Test
    void testSave_WithNullDate_ThrowsException() {
        // Arrange
        LocalDate date = null;

        // Act and Assert
        Exception exception = assertThrows(NullPointerException.class, () -> currencyService.save(date));

        String expectedMessage = "\"date\" is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void deleteByDate_shouldDeleteCurrencyInfo_whenCurrencyInfoExistsForGivenDate() {
        // given
        LocalDate date = LocalDate.now();
        CurrencyInfo currencyInfo = new CurrencyInfo();
        when(currencyInfoRepository.findByDate(anyString())).thenReturn(Optional.of(currencyInfo));

        // when
        currencyService.deleteByDate(date);

        // then
        verify(currencyInfoRepository).findByDate(anyString());
        verify(currencyInfoRepository).delete(currencyInfo);
    }

    @Test
    public void deleteByDate_shouldThrowResourceNotFoundException_whenCurrencyInfoDoesNotExistForGivenDate() {
        // given
        LocalDate date = LocalDate.now();

        when(currencyInfoRepository.findByDate(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.deleteByDate(date));
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyAndDateExist_ReturnsCurrencyInfoRequestModel() {
        // arrange
        String from = "USD";

        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));

        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModel();

        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, from);

        // assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyDoesNotExist_ReturnsCurrencyInfoRequestModelWithoutResult() {
        // arrange
        String from = "EUR";
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));

        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModelWithoutResult();
        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, from);

        // assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByDateAndCurrency_WhenDateDoesNotExist_ThrowsResourceNotFoundException() {
        // arrange
        String from = "USD";
        LocalDate date = LocalDate.now();
        when(currencyInfoRepository.findByDate(anyString())).thenReturn(Optional.empty());

        // assert
        assertThrows(ResourceNotFoundException.class, () -> currencyService.getByDateAndCurrency(date, from));
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyIsNull_ReturnsCurrencyInfoRequestModel() {
        // arrange
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));

        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModel();
        expectedModel.getResult().
                add(CurrencyRequestModel.builder()
                        .name("EUR")
                        .value(BigDecimal.valueOf(1.12))
                        .code("1 Avro")
                        .build());

        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, null);

        // assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyIsEmpty_ReturnsCurrencyInfoRequestModel() {
        // arrange
        String from = "";
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));

        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModel();
        expectedModel.getResult().
                add(CurrencyRequestModel.builder()
                        .name("EUR")
                        .value(BigDecimal.valueOf(1.12))
                        .code("1 Avro")
                        .build());

        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, from);

        // assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyIsBlank_ReturnsCurrencyInfoRequestModel() {
        // arrange
        String from = "   ";
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));
        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModel();
        expectedModel.getResult().
                add(CurrencyRequestModel.builder()
                        .name("EUR")
                        .value(BigDecimal.valueOf(1.12))
                        .code("1 Avro")
                        .build());


        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, from);

        // assert
        assertEquals(expectedModel, actualModel);
    }

    @Test
    void getByDateAndCurrency_WhenCurrencyDoesNotMatch_ReturnsCurrencyInfoRequestModelWithoutResult() {
        // arrange
        String from = "EUR";
        LocalDate date = LocalDate.of(2013, 3, 13);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        CurrencyInfo currencyInfo = CurrencyMockData.getCurrencyInfoMock();

        when(currencyInfoRepository.findByDate(formatDate)).thenReturn(Optional.of(currencyInfo));
        CurrencyInfoRequestModel expectedModel = CurrencyMockData.getCurrencyInfoRequestModelWithoutResult();
        when(currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo)).thenReturn(expectedModel);

        // act
        CurrencyInfoRequestModel actualModel = currencyService.getByDateAndCurrency(date, from);

        // assert
        assertEquals(expectedModel, actualModel);
    }
}