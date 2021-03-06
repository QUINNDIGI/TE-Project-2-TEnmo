package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao{

    private Long accountId;
    private Long userId;
    private BigDecimal balance;
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public  BigDecimal getBalance(Long userId) {
        String sql = "SELECT * FROM account a\n" +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id\n" +
                "WHERE a.user_id = ? ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            Account account = mapRowToAccount(result);
            return account.getBalance();
        }
        return null;
    }

    @Override
    public Account getAccountFromUserId(int userId) {
        String sql = "SELECT * FROM account a\n" +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id\n" +
                "WHERE a.user_id = ? ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            Account account = mapRowToAccount(result);
            return account;
        }
        return null;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
