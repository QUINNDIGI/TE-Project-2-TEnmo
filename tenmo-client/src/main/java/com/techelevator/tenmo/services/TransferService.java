package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public List<User> listUsers(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        List<User> userList = new ArrayList<>();
        try {

            ResponseEntity<List<User>>  response = restTemplate.exchange(baseUrl +"tenmo/listUsers", HttpMethod.GET, entity, new ParameterizedTypeReference<List<User>>(){});
            userList = response.getBody();
            if (userList != null) {
                printUserList(userList);
            }
            return userList;
        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }
        return null;


    }

    private void printUserList(List<User> userList)
    {
        System.out.println ("___________________________________________________________");
        System.out.println ("Users ID      Name");
        System.out.println ("___________________________________________________________");
        for (User user: userList)
        {
            System.out.println (user.getId() +"    "+ user.getUsername());
        }
        System.out.println ("___________________________________________________________");


    }

    public void transferToUser (AuthenticatedUser user, int toUser, BigDecimal amount )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        Transfer transfer = new Transfer();
        //Long toUserLong = toUser;
        transfer.setAccountTo (toUser);
        transfer.setAccountFrom (user.getUser().getId());
        transfer.setAmount(amount);

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        try {

            ResponseEntity<Transfer>  response = restTemplate.exchange(baseUrl +"tenmo/transfer", HttpMethod.POST, entity, Transfer.class);
            Transfer returnedTransfer = response.getBody();
            if (returnedTransfer != null) {

            }
            //return returnedTransfer;
        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }
        //return null;
    }
}
