package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao {

    private Long transferId;
    private Long transferTypeId;
    private Long transferStatusId;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal amount;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> listUsers(Principal userInfo) {

        String sql = "SELECT * FROM tenmo_user ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        List<User> userList = new ArrayList<>();

        while (result.next()) {
            User user = mapRowToUser(result);
            if (userInfo.getName() != user.getUsername())
            {
                userList.add(user);
            }
        }
        return userList;
    }
    private User mapRowToUser(SqlRowSet rowSet) {
        User user = new User();

        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));
        user.setPassword(rowSet.getString("password_hash"));
        return user;
    }

    public Transfer makeTransfer(Transfer transfer)
    {
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES (DEFAULT, ?, ?, ?, ?, ?)";

        Long transferTypeId = transfer.getTransferTypeId();
        Long transferStatusId = transfer.getTransferStatusId();
        Long accountFrom = transfer.getAccountFrom();
        Long accountTo = transfer.getAccountTo();
        BigDecimal amount = transfer.getAmount();

        jdbcTemplate.update(sql, transferTypeId, transferStatusId, accountFrom, accountTo, amount);

        sql = "SELECT balance FROM account " +
                "WHERE account_id = ? ";
        BigDecimal fromBalance = jdbcTemplate.queryForObject(sql, accountFrom);

        fromBalance =fromBalance.subtract(amount);

        sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        jdbcTemplate.update(sql, fromBalance, accountFRom);

        sql = "SELECT balance FROM account " +
                "WHERE account_id = ? ";
        BigDecimal toBalance = jdbcTemplate.queryForObject(sql, accountTo);

        toBalance = toBalance.add(amount);

        sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        jdbcTemplate.update(sql, toBalance, accountTo);


    }

    public List<User> create(Principal userInfo, Transfer transfer) {

        String sql = "INSERT INTO * from transfer " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id "+
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        List<User> userList = new ArrayList<>();

        while (result.next()) {
            User user = mapRowToUser(result);
            if (userInfo.getName() != user.getUsername())
            {
                userList.add(user);
            }
        }
        return userList;
    }



    public List<User> pastTransfers(Principal userInfo) {

        String sql = "SELECT * from transfer " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id "+
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        List<User> userList = new ArrayList<>();

        while (result.next()) {
            User user = mapRowToUser(result);
            if (userInfo.getName() != user.getUsername())
            {
                userList.add(user);
            }
        }
        return userList;
    }

}
