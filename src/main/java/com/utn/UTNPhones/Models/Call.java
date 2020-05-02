package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.stream.Stream;

@Entity
@Table(name="Calls")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Call {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_rate")
    private Rate rate;

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_line_origin")
    private Phoneline originPhoneline;

    @ManyToOne
    @JoinColumn(name = "id_line_destination")
    private Phoneline destinationPhoneline;

    @Column(name = "call_date")
    private Date date;

    @Column(name = "call_price")
    private Integer price;

    @Column(name = "call_duration")
    private Integer duration;

    @Column(name = "call_totalprice")
    private Integer totalPrice;

    public boolean hasNullAtribute(){
        if (Stream.of(originPhoneline,destinationPhoneline,rate,date,price,duration,totalPrice).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}
