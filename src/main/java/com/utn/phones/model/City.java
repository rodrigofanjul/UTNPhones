package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_province")
    @NotNull(message = "Provide province {id}")
    private Province province;

    @Column(name="city_name")
    @NotNull(message = "Provide name (String)")
    private String name;

    @Column(name="city_prefix")
    @NotNull(message = "Provide prefix (Integer)")
    private Integer prefix;
}
