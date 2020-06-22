package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="calls")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Call {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_line_origin")
    @NotNull(message = "Provide origin {id}")
    private Phoneline origin;

    @ManyToOne
    @JoinColumn(name = "id_line_destination")
    @NotNull(message = "Provide destination {id}")
    private Phoneline destination;

    @Column(name = "call_date")
    private Date date;

    @Column(name = "call_rate")
    private Float rate;

    @Column(name = "call_duration")
    @NotNull(message = "Provide duration (seconds)")
    @Min(value = 1, message = "Provide duration > 0 (seconds)")
    private Integer duration;

    @Column(name = "call_price")
    private Float price;
}
