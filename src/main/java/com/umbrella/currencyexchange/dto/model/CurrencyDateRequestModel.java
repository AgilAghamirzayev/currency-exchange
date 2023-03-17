package com.umbrella.currencyexchange.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class CurrencyDateRequestModel {
    private String base;
    private Map<String, Map<String, BigDecimal>> currency;
}
