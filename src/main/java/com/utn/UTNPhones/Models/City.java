package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
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
    private Province province;

    @Column(name="city_name")
    private String name;

    @Column(name="city_prefix")
    private Integer prefix;

    public boolean hasNullAtribute(){
        if (Stream.of(name,prefix,province).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}
