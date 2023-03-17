package com.umbrella.currencyexchange.controller;

import com.umbrella.currencyexchange.dto.model.CurrencyDateRequestModel;
import com.umbrella.currencyexchange.dto.model.CurrencyInfoRequestModel;
import com.umbrella.currencyexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;


    @PostMapping
    public ResponseEntity<String> getAllCurrencies(@RequestParam
                                                   @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                   LocalDate date) {
        return ResponseEntity.ok(currencyService.save(date));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrencyByDate(@RequestParam
                                                     @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        currencyService.deleteByDate(date);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/historical")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<CurrencyInfoRequestModel> getCurrencyByDateAndCurrency(@RequestParam
                                                                          @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                          LocalDate date,
                                                                          @RequestParam(required = false) String from) {
        return ResponseEntity.ok(currencyService.getByDateAndCurrency(date, from));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<CurrencyDateRequestModel> getAllDateCurrenciesByCurrency(@RequestParam String from) {
        return ResponseEntity.ok(currencyService.getAllByCurrency(from));
    }
}
