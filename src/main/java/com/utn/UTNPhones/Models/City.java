package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="Cities")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_province")
    @NotNull
    private Province province;

    @Column(name="city_name")
    @NotNull
    private String name;

    @Column(name="city_prefix")
    @NotNull
    private Integer prefix;
}
