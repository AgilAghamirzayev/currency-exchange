package com.umbrella.currencyexchange.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue
    private Long id;
    private String nominal;
    private String code;
    private String name;
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "currency_info_id")
    private CurrencyInfo currencyInfo;
}
