package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class TransferService {

    private JdbcUserDao userDao;
    private JdbcTransferDao transferDao;

    public TransferService (JdbcUserDao transferDao, JdbcUserDao userDao)
    {

        this.transferDao = transferDao;
        this.userDao = accountDao;
    }
    /*public BigDecimal getBalance(Principal userInfo)
    {
        BigDecimal balance = new BigDecimal ("500.00");

        String username = userInfo.getName();
        User user = userDao.findByUsername(username);
        Long userId = user.getId();
        balance = accountDao.getBalance(userId);

        return balance;
    }*/




}
