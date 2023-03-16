package com.umbrella.currencyexchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CurrencyInfoDto {

    @JacksonXmlProperty(localName = "Date")
    private String date;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "ValType")
    private List<CurrencyDto> currencies;
}