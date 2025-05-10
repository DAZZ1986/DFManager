package com.DFM.DFM_transfer_module.consumer;

import com.DFM.DFM_transfer_module.dto.TransferRequest;
import com.DFM.DFM_transfer_module.service.TransferService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @Autowired
    private TransferService transferService;


    public RabbitMQJsonConsumer(TransferService transferService) {
        this.transferService = transferService;
    }


    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(TransferRequest transferRequest){
        LOGGER.info(String.format("RabbitMQ_Json_Consumer:  Received JSON message -> %s", transferRequest.toString()));

        try {
            Long clubId = transferRequest.getClubId();
            Long playerId = transferRequest.getPlayerId();
            boolean transferStatus = transferRequest.isTransferRequest();

            transferService.transferFinalOperation(clubId, playerId, transferStatus);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

/*
    @RabbitListener(queues = "myQueue")
    public void handleFullMessage(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            Long clubId = root.get("clubId").asLong();
            Long playerId = root.get("playerId").asLong();
            boolean transferRequest = root.get("transferRequest").asBoolean();

            // Обработка данных
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
*/
}
