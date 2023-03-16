package com.umbrella.currencyexchange.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyRequestModel {
    private String nominal;
    private String code;
    private String name;
    private BigDecimal value;
}
