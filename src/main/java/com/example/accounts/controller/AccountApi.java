package com.example.accounts.controller;

import com.example.accounts.config.jwt.JwtTokenProvider;
import com.example.accounts.entity.dto.AccountDto;
import com.example.accounts.entity.request.AccountRequest;
import com.example.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account-API")
@RequiredArgsConstructor
public class AccountApi {
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Get Accounts",
            description = "Return a list of accounts")
    @GetMapping("")
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccount());
    }

    // The idea is to use Firebase for Gmail login
    // The client send the Firebase uid to the server (Encrypted, but not going to do it for now)
    // The server then send the uid to Firebase for validation
    // If success, check if user account is in db
    // if not in db, create a new account
    // Return a JWT token if success
    @PostMapping("/login")
    @Operation(summary = "Get JWT",
            description = "Return JWT token if login success, fail code if invalid Firebase uid")
    public ResponseEntity<?> login(String token){
        //Send token to Firebase

        //Check if account exist in db, create new account in db if not

        //Generate jwt token
        try {
            String jwtToken=jwtTokenProvider.generateToken(token);

            //Return jwt if success, or login fail message
            return ResponseEntity.ok(jwtToken);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PostMapping("/test")
    @Operation(summary = "testing JWT",
            description = "Return true if success, fail code if invalid")
    public ResponseEntity<?> test(String token){
        try {
            boolean result=jwtTokenProvider.validateToken(token);
            return ResponseEntity.ok(result);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @Operation(summary = "Get an Account by Id",
            description = "Return an account by accountId, or a message if no such account in the Database")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccounts(@PathVariable Integer id){
        try {
            AccountDto result=accountService.getById(id);
            if (result!=null)
                return ResponseEntity.ok(result);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such account with ID: "+id);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while getting the account: "+ex.getMessage());
        }
    }
    @PostMapping("")
    @Operation(summary = "Create a list of Accounts",
            description = "Return a NO_CONTENT status code, or a newly created account if there's only one in the list")
    public ResponseEntity<?> postAccount(@RequestBody ArrayList<AccountRequest> accountRequest){
        try {
            List<AccountDto> result=accountService.createAccounts(accountRequest);
            if (result!=null && result.size()==1)
                return ResponseEntity.ok(result);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Create success");
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating account: "+ex.getMessage());
        }
    }
    @Operation(summary = "Update an Account",
            description = "Return updated account")
    @PutMapping("/{id}")
    public ResponseEntity<?> putAccount(@PathVariable Integer id,
                                        @RequestBody AccountRequest accountRequest){
        try {
            AccountDto result=accountService.updateAccount(id, accountRequest);
            if (result!=null)
                return ResponseEntity.ok(result);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No such account found with ID: "+id);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: "+ ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the account: "+ex.getMessage());
        }
    }
    @Operation(summary = "Delete an Account",
            description = "Return no content")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id){
        try {
            String result=accountService.hardDeleteAccount(id);
            return ResponseEntity.ok(result);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: "+ ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the account: "+ex.getMessage());
        }
    }
}
