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
import java.util.stream.Collectors;

@Controller
public class ClubController {

    @Autowired
    private final ClubService clubService;
    @Autowired
    private final PlayerService playerService;
/*
2. Связь между ClubController и ClubService
    В классе ClubController вы также используете аннотацию @Autowired, чтобы внедрить зависимость ClubService. Это
    позволяет контроллеру использовать сервис для обработки запросов и выполнения бизнес-логики, связанной с клубами.
    Тоесть CRUD методы вытащили классом ClubService из интерфейсов JpaRepository, а тут уже в классе ClubController мы
    эти же CRUD методы вытащили из класса ClubService.
    Тоесть прокинули: JpaRepository -> ClubService -> ClubController

    Чистота кода: Ваша структура кода, где контроллер зависит от сервиса, а сервис зависит от репозитория, соответствует
    принципам многослойной архитектуры. Это позволяет отделять уровень представления(контроллер) от уровня
    бизнес-логики(сервис) и уровня доступа к данным(репозиторий).

 */

    public ClubController(ClubService clubService, PlayerService playerService) {
        this.clubService = clubService;
        this.playerService = playerService;
    }


    @GetMapping("/clubs")
    public String findAll(Model model) {
        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubsAtt", clubs);  //model из переменной clubs, сохраняет список клубов в "clubsAtt" и отдает в шаблон club-list.html
        return "club-list";
    }


    @GetMapping("/club-create")
    public String clubCreate(Model model) {             //через model осущ. передача данных из контроллера в представление !!!
        Club club = new Club();
        club.setName("default name");
        model.addAttribute("clubObjParamCreate", club);
        return "/club-create";
    }
    @PostMapping("/club-create")    //когда используется аннотация `@PostMapping`, Spring автоматически связывает данные из тела запроса с параметрами метода контроллера !!!
    public String saveCreatedClub(Club clubObjParam) {  //приняли с фронта объект, сохранили в БД
        clubService.saveClub(clubObjParam);
        return "redirect:/clubs";
    }


    @GetMapping("/club-update/{id}")        //ПО ЭТОЙ СТРОКЕ МЫ ПЕРЕХОДИМ в браузере и далее в МЕТОД !!!!!!!!!!!!!!!!!!!
    public String updateClub(@PathVariable("id") Long id, Model model) {     //выдернули id с урл, и через параметр model данные передадим из бд на фронт
        Club club = clubService.findById(id);                                //вытащили данные из БД
        model.addAttribute("clubObjParamUpdate", club);         //передали данные найденные в БД для отрисовки на фронт
        return "/club-update";              //ТУТ ПОПАДАЕМ В ШАБЛОН thymeleaf !!!!!!!!!!!!!!!!!!!!!!!
    }
    @PostMapping("/club-update")                            //а этот метод вызывается если мы находимся на этой станице и отправляем ПОСТ запрос
    public String saveUpdatedClub(Club clubObjParam) {      //постом отправили данные с фронта в параметр
        clubService.saveClub(clubObjParam);                 //и после сохранили в БД
        return "redirect:/clubs";
    }


    @GetMapping("/club-delete/{id}")
    public String deletedClub(@PathVariable("id") Long id) {
        clubService.deleteById(id);
        return "redirect:/clubs";
    }


    @GetMapping("/club/{id}")
    public String club(@PathVariable("id") Long clubId, Model model) {
        Club club = new Club();
        club = clubService.findById(clubId);
        model.addAttribute("club", club);

        List<Player> players = playerService.findAll();
        List<Player> filteredPlayers = players.stream()
                .filter(player -> player.getClub() != null && player.getClub().getId() == clubId)
                .collect(Collectors.toList());
        model.addAttribute("filteredPlayers", filteredPlayers);
        return "club";
    }

}
