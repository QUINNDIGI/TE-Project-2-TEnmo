package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.util.BasicLogger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private JdbcTemplate jdbcTemplate;
    private RestTemplate restTemplate;

    AccountController (JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    //@ResponseStatus(HttpStatus.)
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    public BigDecimal getBalance() {

        BigDecimal balance = new BigDecimal("1000.00");
        try {
            balance = restTemplate.getForObject("http://localhost:5432/tenmo/" + "getBalance", BigDecimal.class);
            //restTemplate.exchange()

        } catch (RestClientResponseException ex) {
            
            BasicLogger.log("API Error: " + ex.getRawStatusCode() + " " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            BasicLogger.log("API Error: " + ex.getMessage());
        }
        return balance;
    }

}
