package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.ApiTransfer;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
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

            ResponseEntity<ApiTransfer>  response = restTemplate.exchange(baseUrl +"tenmo/transfer", HttpMethod.POST, entity, ApiTransfer.class);
            ApiTransfer apiTransfer = response.getBody();
            if (apiTransfer != null) {
                printTransferDetails(apiTransfer);
            }

        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }

    }
    public void printTransferList(List<ApiTransfer> transferApiList, AuthenticatedUser user)
    {
        System.out.println("___________________________________________________");
        System.out.println("Transfers ID    From/To                      Amount");
        System.out.println("___________________________________________________");

        for (ApiTransfer apiTransfer: transferApiList) {
            String currentUsername = user.getUser().getUsername();
           String transferUsername = apiTransfer.getFromUsername();
            String username = "";
            if (currentUsername.equals(transferUsername )) {
                username = "To: " + apiTransfer.getToUsername();
            } else {
                username = "From: " + apiTransfer.getFromUsername();
            }

            System.out.println(apiTransfer.getTransferId()+"              " +username +"               $" + apiTransfer.getAmount());



        }
    }
    public void printTransferDetails(ApiTransfer transferApi)
    {
        System.out.println("___________________________________________________");
        System.out.println("Transfer Details");
        System.out.println("___________________________________________________");
        System.out.println("Id: " + transferApi.getTransferId());
        System.out.println("From: " + transferApi.getFromUsername());
        System.out.println("To: " + transferApi.getToUsername());
        System.out.println("Type: " + transferApi.getTransferTypeDesc());
        System.out.println("Status: " + transferApi.getTransferStatusDesc());
        BigDecimal amount = transferApi.getAmount();
        amount = amount.setScale(2);
        System.out.println("Amount: $" + amount);
    }
    public List<ApiTransfer> getTransferHistory(AuthenticatedUser user)
    {
        Long userId = user.getUser().getId();
        //public BigDecimal getBalance(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Long> entity = new HttpEntity<>(userId, headers);
        List<ApiTransfer> apiTransfers = new ArrayList<>();
        try {

            ResponseEntity<List<ApiTransfer>> response  = restTemplate.exchange(baseUrl +"/tenmo/alltransfers", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ApiTransfer>>(){});
            apiTransfers = response.getBody();

            printTransferList(apiTransfers, user);
            return apiTransfers;
        } catch (RestClientResponseException | ResourceAccessException e) {
            com.techelevator.util.BasicLogger.log(e.getMessage());
        }
        return null;


    }

}
