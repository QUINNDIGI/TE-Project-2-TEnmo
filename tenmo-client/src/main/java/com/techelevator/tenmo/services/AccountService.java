package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AccountService {

private final RestTemplate restTemplate = new RestTemplate();


//public Account getBalance(BigDecimal balance) {
//
//}
}
