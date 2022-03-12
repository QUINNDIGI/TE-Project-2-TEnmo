package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.JdbcApiTransfer;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.ApiTransfer;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiTransferService {


   // private JdbcUserDao userDao;
    //private JdbcTransferDao transferDao;
    private JdbcApiTransfer apiTransferDao;
    private JdbcTemplate jdbcTemplate;
    private static final String API_BASE_URL = "http://localhost:8080/";

        private RestTemplate restTemplate;

        public ApiTransferService(JdbcApiTransfer apiTransferDao, JdbcTemplate jdbcTemplate) {
            //this.userDao = userDao;
            //this.transferDao = transferDao;
            this.apiTransferDao = apiTransferDao;
            this.jdbcTemplate = jdbcTemplate;
            this.restTemplate = new RestTemplate();
        }

        public ApiTransfer createTransferApiObject(Transfer transfer) {
            ApiTransfer apiTransfer = new ApiTransfer();
            apiTransfer.setAccountFrom(transfer.getAccountFrom());
            apiTransfer.setAccountTo(transfer.getAccountTo());
            apiTransfer.setAmount(transfer.getAmount());
            apiTransfer.setTransferId(transfer.getTransferId());
            apiTransfer.setTransferStatusId(transfer.getTransferStatusId());
            apiTransfer.setTransferTypeId(transfer.getTransferTypeId());

            String sql = "SELECT transfer_status_desc FROM transfer_status " +
                    "WHERE transfer_status_id = ?";
            String statusDesc = jdbcTemplate.queryForObject(sql, String.class, apiTransfer.getTransferStatusId());

            apiTransfer.setTransferStatusDesc(statusDesc);

            sql = "SELECT transfer_type_desc FROM transfer_type " +
                    "WHERE transfer_type_id = ?";
            String typeDesc = jdbcTemplate.queryForObject(sql, String.class, apiTransfer.getTransferTypeId());

            apiTransfer.setTransferTypeDesc(typeDesc);

            sql = "SELECT username FROM tenmo_user "+
            "WHERE user_id = ?";
            String fromUsername = jdbcTemplate.queryForObject(sql, String.class, apiTransfer.getAccountFrom());

            apiTransfer.setFromUsername(fromUsername);


            sql = "SELECT username FROM tenmo_user "+
                    "WHERE user_id = ?";
            String toUsername = jdbcTemplate.queryForObject(sql, String.class, apiTransfer.getAccountTo());

            apiTransfer.setToUsername(toUsername);

            return apiTransfer;
        }



}
