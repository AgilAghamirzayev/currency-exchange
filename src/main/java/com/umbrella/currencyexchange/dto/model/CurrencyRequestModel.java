package com.umbrella.currencyexchange.dto.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CurrencyRequestModel {
    private String nominal;
    private String code;
    private String name;
    private BigDecimal value;
}
