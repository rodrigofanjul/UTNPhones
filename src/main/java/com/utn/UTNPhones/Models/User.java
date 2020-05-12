package com.utn.UTNPhones.Models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_city")
    @NotNull(message = "Provide city {id}")
    private City city;

    @Column(name="user_name")
    @NotNull(message = "Provide name (String)")
    private String name;

    @Column(name = "user_lastname")
    @NotNull(message = "Provide lastname (String)")
    private String lastname;

    @Column(name = "user_idcard")
    @NotNull(message = "Provide idcard (Integer)")
    private Integer idcard;

    @Column(name = "user_password")
    @NotNull(message = "Provide password (String)")
    private String password;

    public enum Role {
        USER, EMPLOYEE;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    @NotNull(message = "Provide role (USER/EMPLOYEE)")
    private Role role;
}

