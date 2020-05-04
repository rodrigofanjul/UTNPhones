package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Phoneline phoneline;

    @Column(name = "invoice_calls_quantity")
    @NotNull
    private Integer callsQuantity;

    @Column(name = "invoice_cost_price")
    @NotNull
    private Integer costPrice;

    @Column(name = "invoice_totalprice")
    @NotNull
    private Integer totalPrice;

    @Column(name = "invoice_date")
    @NotNull
    private Date date;

    @Column(name = "invoice_is_paid")
    @NotNull
    private Boolean isPaid;

    @Column(name = "invoice_expiration_date")
    @NotNull
    private Date expirationDate;
}
