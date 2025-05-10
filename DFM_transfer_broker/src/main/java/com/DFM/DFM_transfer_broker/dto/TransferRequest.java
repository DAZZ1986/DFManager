package com.DFM.DFM_transfer_broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class TransferRequest implements Serializable {
/*
Важные моменты при отправке сущьности в RabbitMQ:
    - Класс сообщения должен быть сериализуемым (implements Serializable )
    - Должен иметь конструктор по умолчанию
    - Все поля должны иметь геттеры и сеттеры
    - Для сложных объектов лучше использовать Jackson аннотации (@JsonInclude и др.)
 */
    @JsonProperty("clubId")
    private Long clubId;

    @JsonProperty("playerId")
    private Long playerId;

    @JsonProperty("transferRequest") // Если имя поля в JSON отличается от имени в классе или
    private boolean transferField;   // имя должно совпадать с JSON.


    public TransferRequest() {

    }


    // Геттеры и сеттеры
    public Long getClubId() {
        return clubId;
    }
    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }
    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    public boolean getTransferField() {
        return transferField;
    }
    public void setTransferField(boolean transferField) {
        this.transferField = transferField;
    }

}
