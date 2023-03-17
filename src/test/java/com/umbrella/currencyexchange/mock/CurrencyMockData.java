package com.umbrella.currencyexchange.mock;

import com.umbrella.currencyexchange.dto.CurrencyDto;
import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyRequestModel;
import com.umbrella.currencyexchange.entity.Currency;
import com.umbrella.currencyexchange.entity.CurrencyInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyMockData {

    public static CurrencyInfo getCurrencyInfoMock() {
        return CurrencyInfo.builder()
                .base("AZN")
                .createdDate(LocalDateTime.now())
                .date("13.03.2013")
                .result(getCurrencyMock())
                .build();
    }

    private static List<Currency> getCurrencyMock() {
        return List.of(
                Currency.builder()
                        .id(1L)
                        .name("1 ABŞ dolları")
                        .code("USD")
                        .value(BigDecimal.valueOf(0.78))
                        .build(),
                Currency.builder()
                        .id(2L)
                        .name("EUR")
                        .value(BigDecimal.valueOf(1.12))
                        .code("1 Avro")
                        .build()
        );
    }


    public static List<Currency> getCurrencyListMock() {
        return List.of(
                Currency.builder()
                        .id(2L)
                        .name("1 ABŞ dolları")
                        .code("USD")
                        .value(BigDecimal.valueOf(1.70))
                        .currencyInfo(getCurrencyInfoMockData2())
                        .build(),
                Currency.builder()
                        .id(1L)
                        .name("1 ABŞ dolları")
                        .code("USD")
                        .value(BigDecimal.valueOf(0.79))
                        .currencyInfo(getCurrencyInfoMockData1())
                        .build()

        );
    }

    private static CurrencyInfo getCurrencyInfoMockData1() {
        return CurrencyInfo.builder()
                .base("AZN")
                .createdDate(LocalDateTime.now())
                .date("11.03.2011")
                .build();
    }


    private static CurrencyInfo getCurrencyInfoMockData2() {
        return CurrencyInfo.builder()
                .base("AZN")
                .createdDate(LocalDateTime.now())
                .date("15.03.2023")
                .build();
    }

    public static CurrencyDateRequestModel getCurrencyDateRequestModelMock() {
        return CurrencyDateRequestModel.builder()
                .base("AZN")
                .currency(

                        Map.of("USD",
                                Map.of(
                                        "15.03.2023", BigDecimal.valueOf(1.70),
                                        "11.03.2011", BigDecimal.valueOf(0.79)
                                )
                        )
                )
                .build();
    }

    public static CurrencyDateRequestModel getCurrencyDateRequestEmptyModelMock() {
        return CurrencyDateRequestModel.builder()
                .base("AZN")
                .currency(Map.of("USD", Map.of()))
                .build();
    }

    public static CurrencyInfoDto getCurrencyInfoDtoMock() {
        return CurrencyInfoDto.builder()
              .base("AZN")
              .date("13.03.2013")
              .result(getCurrencyDtoMock())
              .build();
    }

    private static List<CurrencyDto> getCurrencyDtoMock() {
        return List.of(
                CurrencyDto.builder()
                      .name("1 ABŞ dolları")
                      .code("USD")
                      .value(BigDecimal.valueOf(0.78))
                      .build(),
                CurrencyDto.builder()
                      .name("EUR")
                      .code("1 Avro")
                      .value(BigDecimal.valueOf(1.12))
                      .build()
        );
    }

    public static CurrencyInfoRequestModel getCurrencyInfoRequestModel() {
        return CurrencyInfoRequestModel.builder()
                .base("AZN")
                .date("13.03.2013")
                .result(getCurrencyRequestModelMock())
                .build();
    }

    private static List<CurrencyRequestModel> getCurrencyRequestModelMock() {
        return new ArrayList<>(){{add(CurrencyRequestModel.builder()
                .name("1 ABŞ dolları")
                .code("USD")
                .value(BigDecimal.valueOf(0.78))
                .build());}};
    }

    public static CurrencyInfoRequestModel getCurrencyInfoRequestModelWithoutResult() {
        return CurrencyInfoRequestModel.builder()
                .base("AZN")
                .date("13.03.2013")
                .result(List.of())
                .build();
    }

}
