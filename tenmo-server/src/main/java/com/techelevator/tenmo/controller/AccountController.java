package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.service.AccountService;
import com.techelevator.util.BasicLogger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/tenmo")
@PreAuthorize("isAuthenticated()")
public class AccountController {
        ///private JdbcUserDao userDao = new ;
        private JdbcAccountDao accountDao;
        private AccountService accountService;

    public AccountController(JdbcAccountDao accountDao, AccountService accountService) {
        this.accountDao = accountDao;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal userInfo) {

        BigDecimal balance = new BigDecimal("750.00");

        balance = accountService.getBalance(userInfo);

        return balance;
    }

}
