package com.umbrella.currencyexchange.repository;

import com.umbrella.currencyexchange.entity.CurrencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyInfoRepository extends JpaRepository<CurrencyInfo, Long> {
    Optional<CurrencyInfo> findByDate(String date);

}
