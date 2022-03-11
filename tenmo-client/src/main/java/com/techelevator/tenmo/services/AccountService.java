package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public AccountService(String baseUrl)
    {

        this.baseUrl = baseUrl;
    }

    public BigDecimal getBalance(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        BigDecimal balance;
        try {

        balance  = restTemplate.exchange(baseUrl +"/tenmo/" + "getBalance", HttpMethod.GET, entity, BigDecimal.class).getBody();


            return balance;
        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }
        return null;

    }
}
