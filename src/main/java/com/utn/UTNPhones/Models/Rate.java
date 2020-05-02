package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import java.util.stream.Stream;

@Entity
@Table(name="Rates")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_origin_city")
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "id_destination_city")
    private City destinationCity;

    @Column(name = "rate_per_minute")
    private Integer perMinute;

    public boolean hasNullAtribute(){
        if (Stream.of(originCity,destinationCity,perMinute).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}
