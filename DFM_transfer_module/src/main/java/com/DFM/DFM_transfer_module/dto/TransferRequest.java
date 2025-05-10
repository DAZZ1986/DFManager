package com.DFM.DFM_transfer_module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TransferRequest implements Serializable {

    @JsonProperty("clubId")
    private Long clubId;

    @JsonProperty("playerId")
    private Long playerId;

    @JsonProperty("transferRequest")   //если имя поля в JSON отличается от имени в классе или
    private boolean transferRequest;   //имя должно совпадать с JSON.


    //геттеры и сеттеры
    public Long getClubId() { return clubId; }
    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }
    public boolean isTransferRequest() { return transferRequest; }
    public void setTransferRequest(boolean transferRequest) { this.transferRequest = transferRequest; }
}
