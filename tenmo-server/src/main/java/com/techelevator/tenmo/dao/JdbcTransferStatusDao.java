package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferStatusDao {

    private Long transferStatusId;
    private String transferStatusDescription;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
