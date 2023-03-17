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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CurrencyMapper.class)
public abstract class CurrencyMapper {
    public static CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    public abstract CurrencyInfo mapToCurrencyInfoEntity(CurrencyInfoDto currencyInfoDto);

    public abstract CurrencyInfoRequestModel mapToCurrencyInfoRequestModel(CurrencyInfo currencyInfoByDate);


    public CurrencyDateRequestModel mapToCurrencyDateRequestModel(List<Currency> currencyInfoList, String from) {
        CurrencyDateRequestModel currencyData = new CurrencyDateRequestModel();
        HashMap<String, BigDecimal> dateAndValue = new HashMap<>();


        for (Currency currency : currencyInfoList) {
            dateAndValue.put(currency.getCurrencyInfo().getDate(), currency.getValue());
        }

        currencyData.setBase("AZN");
        currencyData.setCurrency(from);
        currencyData.setDateAndValue(dateAndValue);

        return currencyData;
    }
}
