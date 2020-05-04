package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Rate rate;

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_line_origin")
    @NotNull
    private Phoneline originPhoneline;

    @ManyToOne
    @JoinColumn(name = "id_line_destination")
    @NotNull
    private Phoneline destinationPhoneline;

    @Column(name = "call_date")
    @NotNull
    private Date date;

    @Column(name = "call_price")
    @NotNull
    private Integer price;

    @Column(name = "call_duration")
    @NotNull
    private Integer duration;

    @Column(name = "call_totalprice")
    @NotNull
    private Integer totalPrice;
}
