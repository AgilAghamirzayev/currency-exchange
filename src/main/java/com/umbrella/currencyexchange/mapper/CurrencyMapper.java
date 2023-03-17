package com.umbrella.currencyexchange.mapper;

import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.entity.Currency;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CurrencyMapper.class)
public abstract class CurrencyMapper {
    public static CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    public abstract CurrencyInfo mapToCurrencyInfoEntity(CurrencyInfoDto currencyInfoDto);

    public abstract CurrencyInfoRequestModel mapToCurrencyInfoRequestModel(CurrencyInfo currencyInfoByDate);


    public CurrencyDateRequestModel mapToCurrencyDateRequestModel(List<Currency> currencyInfoList, String from) {
        CurrencyDateRequestModel currencyData = new CurrencyDateRequestModel();

        HashMap<String, Map<String, BigDecimal>> currencyToValue = new HashMap<>();
        HashMap<String, BigDecimal> dateAndValue = new HashMap<>();


        for (Currency currency : currencyInfoList) {
            dateAndValue.put(currency.getCurrencyInfo().getDate(), currency.getValue());
        }


        currencyData.setBase("AZN");
        currencyToValue.put(from, dateAndValue);
        currencyData.setCurrency(currencyToValue);

        return currencyData;
    }
}
