package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.stream.Stream;

@Entity
@Table(name="Invoices")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phoneline")
    private Phoneline phoneline;

    @Column(name = "invoice_calls_quantity")
    private Integer callsQuantity;

    @Column(name = "invoice_cost_price")
    private Integer costPrice;

    @Column(name = "invoice_totalprice")
    private Integer totalPrice;

    @Column(name = "invoice_date")
    private Date date;

    @Column(name = "invoice_is_paid")
    private Boolean isPaid;

    @Column(name = "invoice_expiration_date")
    private Date expirationDate;

    public boolean hasNullAtribute(){
        if (Stream.of(phoneline,callsQuantity,costPrice,totalPrice,isPaid,date,expirationDate).anyMatch(x -> x == null)) {
            return true;
        }else{
            return false;
        }
    }
}
