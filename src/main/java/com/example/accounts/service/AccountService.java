package com.example.accounts.service;

import com.example.accounts.entity.Account;
import com.example.accounts.entity.dto.AccountDto;
import com.example.accounts.entity.request.AccountRequest;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccount();
    AccountDto getById(Integer id);
    List<AccountDto> createAccounts(List<AccountRequest> accountRequests);
    AccountDto updateAccount(Integer id, AccountRequest accountRequest);
    String hardDeleteAccount(Integer id);
}
