package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService()
    {

    }

    public BigDecimal getBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity<>(Account, headers);

        Account returnedAccount = null;
        try {
            returnedAccount  = restTemplate.postForObject( API_BASE_URL +"/tenmo/" + "getBalance", entity, Account.class);
            return returnedAccount.getBalance();
        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }
        return returnedAccount;
        //BigDecimal balance = new BigDecimal("1000.00");
        //System.out.println("Current Balance: " );

    }
}
