package com.umbrella.currencyexchange.controller;

import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;


    @GetMapping
    public ResponseEntity<CurrencyInfoRequestModel> getAllCurrencies(@RequestParam(value = "date")
                                                                     @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                                     LocalDate date) {
        return ResponseEntity.ok(currencyService.getCurrency(date));
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteCurrencyByDate(@RequestParam(value = "date")
                                                     @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        currencyService.deleteCurrencyByDate(date);
        return ResponseEntity.ok().build();
    }
}
