package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.tenmo.model.Account;
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

}
