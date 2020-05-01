package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import java.util.stream.Stream;

@Entity
@Table(name="users")
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

    @Column(name="user_name")
    private String name;

    @Column(name = "user_lastname")
    private String lastname;

    @Column(name = "user_idcard")
    private Integer idcard;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    public boolean hasNullAtribute(){
        if (Stream.of(name,lastname,idcard,password,city).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}

