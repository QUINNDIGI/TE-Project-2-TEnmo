package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferTypeDao {

    private Long transferTypeId;
    private String transferTypeDescription;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
