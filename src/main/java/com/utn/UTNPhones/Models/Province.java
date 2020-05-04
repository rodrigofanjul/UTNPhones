package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="Provinces")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="province_name")
    @NotNull
    private String name;
}
