package com.DFM.DFM_transfer_broker.controller;

import com.DFM.DFM_transfer_broker.dto.TransferRequest;
import com.DFM.DFM_transfer_broker.publisher.RabbitMQJsonProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.amqp.rabbit.connection.NodeLocator.LOGGER;

@Controller
public class MessageJsonController {

    private RabbitMQJsonProducer jsonProducer;


    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }



    @PostMapping("/api/transfer-request")
    public ResponseEntity<String> sendJsonMessage(@RequestBody TransferRequest transferRequestDto){
        jsonProducer.sendJsonMessage(transferRequestDto);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }

}
