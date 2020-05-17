package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="rates")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
