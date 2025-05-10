package com.DFM.DFM_transfer_module.controller;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.model.Player;
import com.DFM.DFM_transfer_module.service.ClubService;
import com.DFM.DFM_transfer_module.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    private final ClubService clubService;
    @Autowired
    private final PlayerService playerService;


    public PlayerController(ClubService clubService, PlayerService playerService) {
        this.clubService = clubService;
        this.playerService = playerService;
    }


/*
Подробный разбор:
    model.addAttribute("playersAtt", players)
        - Добавляет список players в объект Model под ключом "playersAtt".
        - Spring автоматически делает этот объект доступным в Thymeleaf-шаблоне.
    return "player-list"
        - Указывает Spring искать шаблон player-list.html в:
 */
    @GetMapping("/players")                                         //ссылка в браузере / вход в контроллер
    public String findAll(Model model) {                            // из БД на фронт
        List<Player> players = playerService.findAll();             // 1. Получаем данные из сервиса
        model.addAttribute("playersAtt", players);     // 2. Добавляем в модель под ключом "playersAtt". Spring автоматически делает этот объект доступным в Thymeleaf-шаблоне.

        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubs", clubs);
        return "player-list";                                       // 3. Указываем шаблон Thymeleaf   //когда вы возвращаете строку "player-list", Spring ищет шаблон с таким именем (player-list.html)
    }


    @GetMapping("/player-creat")
    public String playerCreat(Model model) {                        //через model осущ. передача данных из контроллера в представление !!!
        Player player = new Player();
        player.setFirstname("default first name");
        player.setLastname("default last name");
        model.addAttribute("playerObjParamCreate", player);
        return "/player-creat";
    }
    @PostMapping("/player-creat")      //когда используется аннотация `@PostMapping`, Spring автоматически связывает данные из тела запроса с параметрами метода контроллера!!
    public String createPlayer(Player playerObjParamCr) {   //приняли данные с фронта с формы и сохранили в БД
        playerService.savePlayer(playerObjParamCr);
        return "redirect:/players";
    }


    @GetMapping("/player-upd/{id}")
    public String updatePlayerForm(@PathVariable("id") Long id, Model model) {  //выдернули id с урл, и через параметр model данные передадим из бд на фронт
        Player player = playerService.findById(id);                             //вытащили данные из БД
        model.addAttribute("playerObjParamUpdate", player);        //передали данные найденные в БД для отрисовки на фронт
        return "player-upd";
    }
    @PostMapping("/player-upd")                             //тут в урл id писать не нужно, тк мы уже на данной странице, и POST запрос был отправлен с нее
    public String updatePlayer(Player playerObjParamUp) {   //с фронта в БД (собрали объект с фронта и приняли в параметре и сохранили в БД)
        playerService.savePlayer(playerObjParamUp);
        return "redirect:/players";
    }


    @GetMapping("/player-delete/{id}")                          //гетаемся на страницу
    public String playerDelete(@PathVariable("id") Long id) {   //выдергиваем id из урл
        playerService.deleteById(id);                           //удаляем из БД по id
        return "redirect:/players";                             //редиректимся на список игроков
    }

}
