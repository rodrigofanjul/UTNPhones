package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "id_destination_city")
    @NotNull
    private City destinationCity;

    @Column(name = "rate_per_minute")
    @NotNull
    private Integer perMinute;
}
