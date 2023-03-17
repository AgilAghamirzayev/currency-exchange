package com.umbrella.currencyexchange.controller;

import com.umbrella.currencyexchange.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    @GetMapping
    public ResponseEntity<String> getToken() {
        return ResponseEntity.ok(Constants.AUTH_TOKEN);
    }
}