package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="invoices")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_phoneline")
    @NotNull(message = "Provide phoneline {id}")
    private Phoneline phoneline;

    @Column(name = "invoice_calls_quantity")
    @NotNull(message = "Provide callsQuantity (Integer)")
    private Integer callsQuantity;

    @Column(name = "invoice_cost_price")
    @NotNull(message = "Provide costPrice (Integer)")
    private Float costPrice;

    @Column(name = "invoice_totalprice")
    @NotNull(message = "Provide totalPrice (Integer)")
    private Float totalPrice;

    @Column(name = "invoice_date")
    @NotNull(message = "Provide date (Date)")
    private Date date;

    @Column(name = "invoice_is_paid")
    @NotNull(message = "Provide isPaid (Boolean)")
    private Boolean isPaid;

    @Column(name = "invoice_expiration_date")
    @NotNull(message = "Provide expirationDate (Date)")
    private Date expirationDate;
}
