package com.gaga.auth_server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "email")
    private String email;

    @Column(length = 400, name = "password")
    private String password;

    @Column(length = 400, name = "salt")
    private String salt;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private int birth;
}
