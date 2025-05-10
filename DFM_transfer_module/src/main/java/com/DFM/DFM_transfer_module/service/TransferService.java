package com.DFM.DFM_transfer_module.service;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.model.Player;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    @Autowired
    private final ClubService clubService;
    @Autowired
    private final PlayerService playerService;


    public TransferService(ClubService clubService, PlayerService playerService) {
        this.clubService = clubService;
        this.playerService = playerService;
    }


    //send REST request for transfer
    public boolean transferRequestOperation(Long clubId, Long playerId) {
        Club club = clubService.findById(clubId);           //получаем клуб из базы
        Player player = playerService.findById(playerId);   //получаем игрока из базы

        if(player.getPrice() <= club.getBalance()) {
            String url = "http://localhost:8081/api/transfer-request";  // URL вашего второго проекта

            //Создание JSON-объекта запроса:
/*
            //вариант 1
            String jsonBody = "{ " +
                    "\"clubId\": " + clubId + ", " +
                    "\"playerId\": " + playerId + ", " +
                    "\"transferRequest\": true" +
                    " }";
*/
            //вариант 2
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("clubId", clubId);
            jsonObject.addProperty("playerId", playerId);
            jsonObject.addProperty("transferRequest", true);

            String jsonBody = jsonObject.toString();

            // Отправка POST-запроса
            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)  // Установка типа контента
                    .body(jsonBody)                 // Установка тела запроса
                    .when()
                    .post(url);                     // Отправка запроса

            // Проверка статуса ответа
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());
            return true;
        } else {
            System.out.println("Transfer failed! (money not enough)");
            return false;
        }
    }

    //read message from rabbitMQ for final transfer operation
    @Transactional
    public boolean transferFinalOperation(Long clubId, Long playerId, Boolean transferStatus) {
        if(transferStatus) {
            Club club = clubService.findById(clubId);           //получаем клуб из базы
            Player player = playerService.findById(playerId);   //получаем игрока из базы

            // Явно инициализируем необходимые поля
            Hibernate.initialize(club);
            Hibernate.initialize(player);

            // обновление клуба игрока в базе данных
            player.setClub(club);
            playerService.savePlayer(player);

            // обновление баланса клуба
            int playerPrice = player.getPrice();
            int refreshBalanceClub = club.getBalance() - playerPrice;
            club.setBalance(refreshBalanceClub);
            clubService.saveClub(club);

            System.out.println("Transfer Done!");
            return true;
        } else {
            return false;
        }
    }

}
