package com.umbrella.currencyexchange.service;

import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;

import java.time.LocalDate;

public interface CurrencyService {
    CurrencyInfoRequestModel getCurrency(LocalDate localDate);

    void deleteCurrencyByDate(LocalDate localDate);
}
