package com.umbrella.currencyexchange.dto.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class CurrencyDateRequestModel {
    private String base;
    private String currency;
    private Map<String, BigDecimal> dateAndValue;
}
