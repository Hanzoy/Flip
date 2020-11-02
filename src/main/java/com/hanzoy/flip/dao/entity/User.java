package com.hanzoy.flip.dao.entity;

import com.hanzoy.flip.utils.Token;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @Token
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uuid;

    @Token
    private String username;
    private String password;
}
