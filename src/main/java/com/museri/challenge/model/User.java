package com.museri.challenge.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table (name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones= new ArrayList<>();
    private LocalDateTime lastLogin;
    private LocalDateTime created;
}
