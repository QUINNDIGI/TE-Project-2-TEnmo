package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

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

}
