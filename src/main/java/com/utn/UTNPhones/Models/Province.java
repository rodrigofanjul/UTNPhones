package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import java.util.stream.Stream;

@Entity
@Table(name="provinces")
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
    private String name;

    public boolean hasNullAtribute(){
        if (Stream.of(name).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}