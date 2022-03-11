package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
public class TransferService {

    private JdbcUserDao userDao;
    private JdbcTransferDao transferDao;

    public TransferService (JdbcTransferDao transferDao, JdbcUserDao userDao)
    {

        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    public List<User> listUsers(Principal userInfo)
    {
        List<User> userList = transferDao.listUsers(userInfo);
        return userList;
    }
    public Transfer makeTransfer(Long fromUser, int toUser, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setAccountFrom (fromUser);
        transfer.setAccountTo(toUser);
        transfer.setTransferStatusId(2L);
        transfer.setTransferTypeId(2L);

        transferDao.makeTransfer(transfer);
        return transfer;
    }


}
