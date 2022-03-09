package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao {

    private Long accountId;
    private Long userId;
    private BigDecimal balance;
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getBalance(Long accountId, Long userId) {
        String sql = "SELECT a.user_id, a.account_id FROM account a\n" +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id\n" +
                "WHERE user_id = ? AND account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        if (result.next()) {
            return mapRowToAccount(result);
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


//    private User mapRowToUser(SqlRowSet rs) {
//        User user = new User();
//        user.setId(rs.getLong("user_id"));
//        user.setUsername(rs.getString("username"));
//        user.setPassword(rs.getString("password_hash"));
//        user.setActivated(true);
//        user.setAuthorities("USER");
//        return user;
}
