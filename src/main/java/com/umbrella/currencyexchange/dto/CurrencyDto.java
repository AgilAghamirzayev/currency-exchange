package com.umbrella.currencyexchange.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class CurrencyDto {

    @JacksonXmlProperty(localName = "Nominal")
    private String nominal;
    @JacksonXmlProperty(localName = "Code")
    private String code;
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Value")
    private BigDecimal value;

}