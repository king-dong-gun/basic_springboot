package com.come1997.backboard.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long memberid;

    @Column(unique = true, length = 100)
    private String username;

    @Column(unique = true, length = 150)
    private String email;


    private String password;

}
