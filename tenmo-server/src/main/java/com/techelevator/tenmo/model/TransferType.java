package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TransferType {

    @NotNull
    @JsonProperty("transfer_type_id")
    private Long transferTypeId;
    @NotNull
    @JsonProperty("transfer_type_desc")
    private String transferTypeDescription;


    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferType() {
        return transferTypeDescription;
    }

    public void setTransferType(String transferType) {
        this.transferTypeDescription = transferType;
    }
}
