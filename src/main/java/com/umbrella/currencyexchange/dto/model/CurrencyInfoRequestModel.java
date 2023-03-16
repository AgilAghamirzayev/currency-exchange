package com.umbrella.currencyexchange.dto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(of = "date")
public class CurrencyInfoRequestModel {
    private String date;
    private String name;
    private List<CurrencyRequestModel> currencies = new ArrayList<>();
}
