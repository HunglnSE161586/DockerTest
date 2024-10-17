package com.example.accounts.service;

import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.dto.AccountDto;
import com.example.accounts.entity.request.AccountRequest;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.utils.UpdateNonNullFieldsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private static final String DELETE_ACCOUNT_MESSAGE="Account Deleted!";

    @Override
    public List<AccountDto> getAccount() {
        return accountRepository.findAll().stream().map(AccountMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        return AccountMapper.INSTANCE.toDto( accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No account found with ID: " + id)));
    }

    @Override
    public List<AccountDto> createAccounts(List<AccountRequest> accountRequests) {
        if (accountRequests.isEmpty() || accountRequests==null){
            throw new IllegalArgumentException("Account request cannot be null");
        }
        List<AccountDto> resultList = new ArrayList<>();
        for (AccountRequest accountRequest:accountRequests) {
            resultList.add(
                    AccountMapper.INSTANCE.toDto(
                            accountRepository.save(AccountMapper.INSTANCE.toEntity(accountRequest)
                            ))
            );
        }
        return resultList;
    }

    @Override
    public AccountDto updateAccount(Integer id, AccountRequest accountRequest) {
        if (id == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        if (accountRequest == null) {
            throw new IllegalArgumentException("Account request cannot be null");
        }
        Account oldAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No account found with ID: " + id));

        UpdateNonNullFieldsUtil.updateNonNullFields(oldAccount, accountRequest);
        return AccountMapper.INSTANCE.toDto(accountRepository.save(oldAccount));
    }

    @Override
    public String hardDeleteAccount(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        Account oldAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No account found with ID: " + id));
        accountRepository.delete(oldAccount);
        return DELETE_ACCOUNT_MESSAGE;
    }
}
