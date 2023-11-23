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
    @GeneratedValue
    private java.util.UUID id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones= new ArrayList<>();
    private String token;
    private LocalDateTime lastLogin;
    private LocalDateTime created;
}
