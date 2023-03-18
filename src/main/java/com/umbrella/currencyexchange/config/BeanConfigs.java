package com.umbrella.currencyexchange.config;

import com.umbrella.currencyexchange.exception.handler.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfigs {

    @Bean
    public PasswordEncoder passwordConf() {
        return new BCryptPasswordEncoder();
    }

}
