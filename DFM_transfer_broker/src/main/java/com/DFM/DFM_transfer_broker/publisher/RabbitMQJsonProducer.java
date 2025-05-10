package com.DFM.DFM_transfer_broker.publisher;

import com.DFM.DFM_transfer_broker.dto.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);
    private RabbitTemplate rabbitTemplate;


    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }




    public void sendJsonMessage(TransferRequest transferField){
        LOGGER.info(String.format("!!!!!!!!!!  @@@@@@@@@@@@@@  ###############  JSON message sent -> %s", transferField.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, transferField);
    }

/*
    //SEND message
    @PostMapping("/api/transfer-request")   //принимаем JSON и десериализуем его раскладывая по полям класса TransferRequest
    public ResponseEntity<String> transferRequest(@RequestBody TransferRequest transferField) { //параметр transferField тут может не совпадать с
        //именем в JSON при условии что на поле весит аннотация @JsonProperty("transferRequest") которая совпадает с именем поля в JSON.

        LOGGER.info(String.format("Json message sent -> %s", transferField.toString()));

        //rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
        rabbitTemplate.convertAndSend(queue.getName(), transferField);  // Автоматически конвертируется в JSON

        boolean receivedTransReq = transferField.getTransferField();
        System.out.println("Received field: " + receivedTransReq);

        return new ResponseEntity<>("Received: " + receivedTransReq, HttpStatus.OK);  // Возвращаем ответ
    }
*/
}
