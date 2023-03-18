package com.umbrella.currencyexchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyInfoDto {

    @JacksonXmlProperty(localName = "Date")
    private String date;

    @JacksonXmlProperty(localName = "Name")
    private String base;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @XmlElement(name = "Valute")
    @XmlElementWrapper(name = "ValType")
    @JacksonXmlProperty(localName = "ValType")
    private List<CurrencyDto> result;
}
