package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "phoneline_number")
    private Integer number;

    @Column(name = "phoneline_type")
    private String type;

    public boolean hasNullAtribute(){
        if (Stream.of(number,type,city,user).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}
