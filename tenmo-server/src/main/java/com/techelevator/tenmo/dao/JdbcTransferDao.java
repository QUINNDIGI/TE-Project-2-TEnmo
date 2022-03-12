package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.ApiTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.ApiTransferService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.rowset.JdbcRowSet;
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
    private ApiTransferService apiTransferService;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, ApiTransferService apiTransferService) {
        this.jdbcTemplate = jdbcTemplate;
        this.apiTransferService = apiTransferService;
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
                "VALUES (DEFAULT, ?, ?, ?, ?, ?) RETURNING transfer_id";

        Long transferTypeId = transfer.getTransferTypeId();
        Long transferStatusId = transfer.getTransferStatusId();
        Long accountFrom = transfer.getAccountFrom();
        int accountTo = transfer.getAccountTo();
        BigDecimal amount = transfer.getAmount();

        Long transferId = jdbcTemplate.queryForObject(sql, Long.class, transferTypeId, transferStatusId, accountFrom, accountTo, amount);


        sql = "SELECT balance FROM account " +
                "WHERE account_id = ? ";
        BigDecimal fromBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountFrom);

        fromBalance =fromBalance.subtract(amount);

        sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        jdbcTemplate.update(sql, fromBalance, accountFrom);

        sql = "SELECT balance FROM account " +
                "WHERE account_id = ? ";
        BigDecimal toBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountTo);

        toBalance = toBalance.add(amount);

        sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        jdbcTemplate.update(sql, toBalance, accountTo);

        transfer.setTransferId(transferId);
//        ApiTransfer apiTransfer = apiTransferService.createTransferApiObject(transfer, fromUserId, toUserId);

        return transfer;
    }

    public List<ApiTransfer> getPastTransfers(Principal userInfo)
    {

        String username =  userInfo.getName();

        String sql = "SELECT user_id from tenmo_user WHERE username = ?";

        Long userId = jdbcTemplate.queryForObject(sql,Long.class, username);

        sql = "SELECT account_id from account WHERE user_id = ?";
        Long userAccount = jdbcTemplate.queryForObject(sql, Long.class, userId);

        List<ApiTransfer> apiTransferList = new ArrayList<>();
        sql = "SELECT * from transfer "+
                "WHERE account_from = ?";


        SqlRowSet transferRows = jdbcTemplate.queryForRowSet(sql, userAccount);

        while (transferRows.next())
        {
            Transfer transfer = mapRowToTransfer(transferRows);

            ApiTransfer apiTransfer = apiTransferService.createTransferApiObject(transfer, transfer.getAccountFrom(), transfer.getAccountTo());
            apiTransferList.add(apiTransfer);

        }
        return apiTransferList;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }



}
