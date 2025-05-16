package com.DFM.DFM_transfer_module.service;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private final ClubRepository clubRepository;    // Spring создаст экземпляр ClubRepository
/*
    Автоматическое внедрение зависимостей:
    При использовании @Autowired, Spring автоматически находит и предоставляет нужный экземпляр зависимости. Например,
    если у вас есть класс ClubService, который зависит от ClubRepository, вы можете просто пометить поле clubRepository
    аннотацией @Autowired, и Spring сам создаст и внедрит нужный экземпляр.

    ТОЕСТЬ: когда Spring создает экземпляр ClubService, он также создает экземпляр ClubRepository(если его еще нет)
    и внедряет его в ClubService. Таким образом, вы получаете доступ к методам ClubRepository через экземпляр, который управляется Spring.
 */



/*
    1. Связь между ClubService и ClubRepository
    В классе ClubService вы используете аннотацию @Autowired для внедрения зависимости ClubRepository. Это означает,
    что Spring будет автоматически предоставлять реализацию ClubRepository, когда создается экземпляр ClubService.
    Таким образом, ClubService может использовать методы ClubRepository для выполнения операций с базой данных, связанных с сущностью Club.
*/


    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }




    public Long count(Long id) {
        return clubRepository.count();
    }

    @Transactional
    public Club findById(Long id) {
        return clubRepository.getOne(id);
    }

    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    public Club saveClub(Club club) {
        return clubRepository.save(club);
    }

    public void deleteById(Long id) {
        clubRepository.deleteById(id);
    }

}
