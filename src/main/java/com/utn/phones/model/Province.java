package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="provinces")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="province_name")
    @NotNull(message = "Provide name (String)")
    private String name;
}
