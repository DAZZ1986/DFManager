package com.DFM.DFM_transfer_module.controller;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.model.Player;
import com.DFM.DFM_transfer_module.service.ClubService;
import com.DFM.DFM_transfer_module.service.PlayerService;
import com.DFM.DFM_transfer_module.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransferController {

    @Autowired
    private final ClubService clubService;
    @Autowired
    private final PlayerService playerService;
    @Autowired
    private final TransferService transferService;


    public TransferController(ClubService clubService, PlayerService playerService, TransferService transferService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.transferService = transferService;
    }




    @GetMapping("/transfer-list")
    public String transferList(Model model) {
        List<Player> allPlayers = playerService.findAll();
        List<Player> transferPlayersList = allPlayers.stream()
                .filter(item -> item != null && item.getClub() == null)
                .collect(Collectors.toList());
        model.addAttribute("transferPlayersList", transferPlayersList);

        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubsList", clubs);
        return "transfer-list";
    }

    @PostMapping("/transfer-list")
    public String buyPlayer(
            @RequestParam("clubId") Long clubId,       //передали с фронта на бэк через урл в виде параметров
            @RequestParam("playerId") Long playerId,
            Model model) {
        boolean transferStatus = transferService.transferRequestOperation(clubId, playerId);    //попытка трансфера
/*
        // Обновляем данные в модели (это не актуально, тк рефрешу js_ом страницу для обновления данных)
        List<Player> allPlayers = playerService.findAll();
        List<Player> transferPlayersList = allPlayers.stream()
                .filter(item -> item != null && item.getClub() == null)
                .collect(Collectors.toList());
        model.addAttribute("players", transferPlayersList);
        model.addAttribute("clubs", clubService.findAll());
*/
        return "redirect:/transfer-list";  // Ваш текущий transfer-list просто возвращает строку "transfer-list", но это не приведёт к обновлению страницы, лучше сделать редирект.
    }

    //сделать класс TransferService и перенести туда логику проверки перед покупкой если денег достаточно то TransferService отправляет рестом запрос в BrokerManager - ok
    //BrokerManager генерит сообщение в раббит - ok
    //TransferService слушает раббит и завершает покупку - ok
}
