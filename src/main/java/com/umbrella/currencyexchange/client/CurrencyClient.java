package com.umbrella.currencyexchange.client;


import com.umbrella.currencyexchange.dto.CurrencyInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CurrencyClient", url = "${api.currency}")
public interface CurrencyClient {
    @GetMapping("/{date}.xml")
    CurrencyInfoDto getCurrencyInfo(@PathVariable("date") String date);

}
