package com.umbrella.currencyexchange.dto.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDateRequestModel {
    private String base;
    private Map<String, Map<String, BigDecimal>> currency;
}
