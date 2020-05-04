package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="Phonelines")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Phoneline {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @NotNull
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_city")
    @NotNull
    private City city;

    @Column(name = "phoneline_type")
    @NotNull
    private String type;

    @Column(name = "phoneline_active")
    @NotNull
    private Boolean active;
}
