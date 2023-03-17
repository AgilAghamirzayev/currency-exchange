package com.umbrella.currencyexchange.repository;

import com.umbrella.currencyexchange.entity.Currency;
import com.umbrella.currencyexchange.entity.CurrencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyInfoRepository extends JpaRepository<CurrencyInfo, Long> {
    Optional<CurrencyInfo> findByDate(String date);

//    @Query("SELECT ci FROM CurrencyInfo ci JOIN ci.result c WHERE c.code = :frm")
//    List<CurrencyInfo> getAllByCurrency(@Param("frm") String frm);

    @Query("SELECT c FROM Currency c JOIN c.currencyInfo ci WHERE c.code = :frm")
    List<Currency> getAllByCurrency(@Param("frm") String frm);


}
