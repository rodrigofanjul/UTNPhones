package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="rates")
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
    @NotNull(message = "Provide origin {id}")
    private City origin;

    @ManyToOne
    @JoinColumn(name = "id_destination_city")
    @NotNull(message = "Provide destination {id}")
    private City destination;

    @Column(name = "rate_per_minute")
    @NotNull(message = "Provide rate (Float)")
    private Float rate;
}
