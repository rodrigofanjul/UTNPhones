package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_city")
    @NotNull
    private City city;

    @Column(name="user_name")
    @NotNull
    private String name;

    @Column(name = "user_lastname")
    @NotNull
    private String lastname;

    @Column(name = "user_idcard")
    @NotNull
    private Integer idcard;

    @Column(name = "user_password")
    @NotNull
    private String password;

    @Column(name = "user_type")
    @NotNull
    private String type;
}

