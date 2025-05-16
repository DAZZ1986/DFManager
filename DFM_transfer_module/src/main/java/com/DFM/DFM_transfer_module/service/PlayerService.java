package com.DFM.DFM_transfer_module.service;

import com.DFM.DFM_transfer_module.model.Player;
import com.DFM.DFM_transfer_module.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;


    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Transactional
    public Player findById(Long id) {
        return playerRepository.getOne(id);
    }
    public List<Player> findAll() {
        return playerRepository.findAll();
    }
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }
/*
    public List<Player> playerClubId(Long clubId) {
        return playerRepository.
    }
*/
}
