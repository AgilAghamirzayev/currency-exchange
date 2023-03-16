package com.umbrella.currencyexchange.mapper;

import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyInfo toCurrencyInfoEntity(CurrencyInfoDto currencyInfoDto);

    CurrencyInfoRequestModel toCurrencyInfoRequestModel(CurrencyInfo currencyInfoByDate);
}
