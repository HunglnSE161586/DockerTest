package com.example.accounts.mapper;

import com.example.accounts.entity.Account;
import com.example.accounts.entity.dto.AccountDto;
import com.example.accounts.entity.request.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE= Mappers.getMapper(AccountMapper.class);
    AccountDto toDto(Account account);
    Account toEntity(AccountRequest accountRequest);
}
