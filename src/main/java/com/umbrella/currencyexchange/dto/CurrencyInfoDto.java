package com.umbrella.currencyexchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class CurrencyInfoDto {

    @JacksonXmlProperty(localName = "Date")
    private String date;

    @JacksonXmlProperty(localName = "Name")
    private String base;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "ValType")
    private List<CurrencyDto> result;
}
