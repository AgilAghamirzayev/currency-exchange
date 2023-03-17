package com.umbrella.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "date", "base"})
public class CurrencyInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String date;
    private String base;
    private String description;

    @CreationTimestamp
    private LocalDateTime  createdDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_info_id")
    private List<Currency> result = new ArrayList<>();

}
