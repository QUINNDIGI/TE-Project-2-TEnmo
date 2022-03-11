package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Transfer {

    @JsonProperty("transfer_id")
    @NotNull
    private Long transferId;
    @NotNull
    @JsonProperty("transfer_type_id")
    @NotNull
    private Long transferTypeId;
    @NotNull
    @JsonProperty("transfer_status_id")
    private Long transferStatusId;
    @NotNull
    @JsonProperty("account_from")
    private Long accountFrom;
    @NotNull
    @JsonProperty("account_to")
    private int accountTo;
    @NotNull
    @JsonProperty("amount")
    @DecimalMin(value="0.01")
    private BigDecimal amount;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
