package com.umbrella.currencyexchange.service.impl;

import com.umbrella.currencyexchange.client.CurrencyClient;
import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyRequestModel;
import com.umbrella.currencyexchange.entity.Currency;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import com.umbrella.currencyexchange.exception.ResourceNotFoundException;
import com.umbrella.currencyexchange.mapper.CurrencyMapper;
import com.umbrella.currencyexchange.repository.CurrencyInfoRepository;
import com.umbrella.currencyexchange.service.CurrencyService;
import com.umbrella.currencyexchange.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyServiceImp implements CurrencyService {

    private final CurrencyMapper currencyMapper = CurrencyMapper.INSTANCE;
    private final CurrencyClient currencyClient;
    private final CurrencyInfoRepository currencyInfoRepository;

    @Override
    public String save(LocalDate date) {
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Optional<CurrencyInfo> currencyInfoByDate = currencyInfoRepository.findByDate(formatDate);

        log.info("Got currency from database: {}", currencyInfoByDate);

        if (currencyInfoByDate.isPresent()) {
            CurrencyInfoRequestModel currencyInfoRequestModel = currencyMapper.mapToCurrencyInfoRequestModel(currencyInfoByDate.get());
            log.info("Mapped to currency info request model: {}", currencyInfoRequestModel);
            return Constants.DATABASE_MESSAGE.formatted(date);
        }

        log.info("Getting currency from API");
        CurrencyInfoDto currencyInfoDto = currencyClient.getCurrencyInfo(formatDate);

        CurrencyInfo currencyInfo = currencyMapper.mapToCurrencyInfoEntity(currencyInfoDto);

        currencyInfoRepository.save(currencyInfo);
        log.info("Saved currency to database");

        return Constants.API_MESSAGE.formatted(date);
    }

    @Override
    public void deleteByDate(LocalDate date) {
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        CurrencyInfo currencyInfo = currencyInfoRepository.findByDate(formatDate)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found by date: " + date));

        currencyInfoRepository.delete(currencyInfo);
        log.info("Deleted currency from database");
    }

    @Override
    public CurrencyInfoRequestModel getByDateAndCurrency(LocalDate date, String from) {
        log.info("Getting by date: {} and currency: {}", date, from);
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        CurrencyInfo currencyInfo = currencyInfoRepository.findByDate(formatDate)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found by date: " + date));

        CurrencyInfoRequestModel requestModel = currencyMapper.mapToCurrencyInfoRequestModel(currencyInfo);

        if (from == null || from.isEmpty() || from.isBlank()) {
            return requestModel;
        }

        List<CurrencyRequestModel> currencyList = requestModel.getResult()
                .stream()
                .filter(currency -> currency.getCode().equalsIgnoreCase(from))
                .toList();

        requestModel.setResult(currencyList);

        return requestModel;
    }

    @Override
    public CurrencyDateRequestModel getAllByCurrency(String from) {
        log.info("Getting all by: {}", from);
        List<Currency> currencyInfoList = currencyInfoRepository.getAllByCurrency(from);
        log.info("Got currency from database: {}", currencyInfoList);

        return currencyMapper.mapToCurrencyDateRequestModel(currencyInfoList, from);
    }
}
