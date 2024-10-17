package com.example.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "Account")
@Table(name = "Accounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    private String email;
    private String avatar;
    private String fullName;
    private String phone;
}
