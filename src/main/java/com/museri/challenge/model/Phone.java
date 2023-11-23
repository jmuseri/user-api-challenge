package com.museri.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "phones")
public class Phone implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

}
