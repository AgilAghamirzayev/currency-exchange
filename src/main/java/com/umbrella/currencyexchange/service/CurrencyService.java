package com.umbrella.currencyexchange.service;

import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;

import java.time.LocalDate;

public interface CurrencyService {
    String save(LocalDate date);
    void deleteByDate(LocalDate date);
    CurrencyInfoRequestModel getByDateAndCurrency(LocalDate date, String from);
    CurrencyDateRequestModel getAllByCurrency(String from);
}
