package com.example.accounts.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountRequest {
    private String email;
    private String avatar;
    private String fullName;
    private String phone;
}
