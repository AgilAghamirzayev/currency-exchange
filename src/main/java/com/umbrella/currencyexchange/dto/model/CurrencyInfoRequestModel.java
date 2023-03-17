package com.umbrella.currencyexchange.dto.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class CurrencyInfoRequestModel {
    private String date;
    private String base;
    private List<CurrencyRequestModel> result = new ArrayList<>();
}
