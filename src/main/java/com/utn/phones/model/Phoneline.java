package com.utn.phones.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="phonelines")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Phoneline {
    @Id
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
        MOBILE, LANDLINE;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "phoneline_type")
    @NotNull(message = "Provide type (MOBILE/LANDLINE)")
    private Type type;

    public enum Status {
        ACTIVE, SUSPENDED, CANCELLED;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "phoneline_status")
    @NotNull(message = "Provide status (ACTIVE/SUSPENDED/CANCELLED)")
    private Status status;
}
