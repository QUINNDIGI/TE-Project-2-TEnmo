package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.security.Principal;


@Service
public class AccountService {

    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    public AccountService (JdbcUserDao userDao, JdbcAccountDao accountDao)
    {

        this.userDao = userDao;
        this.accountDao = accountDao;
    }
    public BigDecimal getBalance(Principal userInfo)
    {
        BigDecimal balance = new BigDecimal ("500.00");

        String username = userInfo.getName();
        User user = userDao.findByUsername(username);
        Long userId = user.getId();
        balance = accountDao.getBalance(userId);

        return balance;
    }
}
