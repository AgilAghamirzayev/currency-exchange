package com.umbrella.currencyexchange.service.impl;

import com.umbrella.currencyexchange.client.CurrencyClient;
import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import com.umbrella.currencyexchange.exception.ResourceNotFoundException;
import com.umbrella.currencyexchange.mapper.CurrencyMapper;
import com.umbrella.currencyexchange.repository.CurrencyInfoRepository;
import com.umbrella.currencyexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyServiceImp implements CurrencyService {

    private final CurrencyMapper currencyMapper = CurrencyMapper.INSTANCE;
    private final CurrencyClient currencyClient;
    private final CurrencyInfoRepository currencyInfoRepository;

    @Override
    public CurrencyInfoRequestModel getCurrency(LocalDate date) {
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Optional<CurrencyInfo> currencyInfoByDate = currencyInfoRepository.findByDate(formatDate);

        log.info("Got currency from database: {}", currencyInfoByDate);

        if (currencyInfoByDate.isPresent()) {
            CurrencyInfoRequestModel currencyInfoRequestModel = currencyMapper.toCurrencyInfoRequestModel(currencyInfoByDate.get());
            log.info("Mapped to currency info request model: {}", currencyInfoRequestModel);
            return currencyInfoRequestModel;
        }

        log.info("Getting currency from API");
        CurrencyInfoDto currencyInfoDto = currencyClient.getCurrencyInfo(formatDate);

        CurrencyInfo currencyInfo = currencyMapper.toCurrencyInfoEntity(currencyInfoDto);

        CurrencyInfo savedCurrencyInfo = currencyInfoRepository.save(currencyInfo);
        log.info("Saved currency to database");

        return currencyMapper.toCurrencyInfoRequestModel(savedCurrencyInfo);
    }

    @Override
    public void deleteCurrencyByDate(LocalDate date) {
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        CurrencyInfo currencyInfo = currencyInfoRepository.findByDate(formatDate)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found by date: " + date));

        currencyInfoRepository.delete(currencyInfo);
        log.info("Deleted currency from database");
    }
}
