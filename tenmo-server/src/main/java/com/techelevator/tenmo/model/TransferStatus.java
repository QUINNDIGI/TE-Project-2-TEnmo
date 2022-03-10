package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;

public class TransferStatus {

    @NotNull
    @JsonProperty("transfer_status_id")
    private Long transferStatusId;
    @NotNull
    @JsonProperty("transfer_status_desc")
    private String transferStatusDescription;

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }
}
