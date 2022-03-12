package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.ApiTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.AccountService;
import com.techelevator.tenmo.service.ApiTransferService;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.TransformerFactory;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tenmo")
//@PreAuthorize("isAuthenticated()")
public class TransferController {
    JdbcTransferDao transferDao;
    TransferService transferService;
    ApiTransferService apiTransferService;

    public TransferController(JdbcTransferDao transferDao, TransferService transferService, ApiTransferService apiTransferService)
    {
        this.transferDao = transferDao;
        this.transferService = transferService;
        this.apiTransferService = apiTransferService;
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<User> listUsers(Principal userInfo) {

        List<User> userList = new ArrayList<>();

        userList = transferService.listUsers(userInfo);

        return userList;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ApiTransfer makeTransfer(@RequestBody Transfer transfer) {
//TODO add @Valid back to above ^
        Transfer returnedTransfer = transferService.makeTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        ApiTransfer apiTransfer = apiTransferService.createTransferApiObject(returnedTransfer);
        return apiTransfer;
    }
}
