package com.utn.UTNPhones.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

@Entity
@Table(name="phonelines")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Phoneline {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @NotNull(message = "Provide id (Long)")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NotNull(message = "Provide user {id}")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_city")
    @NotNull(message = "Provide city {id}}")
    private City city;

    public enum Type {
        mobile, landline;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "phoneline_type")
    @NotNull(message = "Provide type (mobile/landline)")
    private Type type;

    public enum Status {
        active, suspended, cancelled;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "phoneline_status")
    @NotNull(message = "Provide status (active/suspended/cancelled)")
    private Status status;
}
