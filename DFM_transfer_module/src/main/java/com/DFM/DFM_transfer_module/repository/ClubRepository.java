package com.DFM.DFM_transfer_module.repository;

import com.DFM.DFM_transfer_module.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> { // Используя JpaRepository, вы получаете доступ к множеству методов для выполнения операций CRUD
    // Здесь можно добавить дополнительные методы, если необходимо

    /*
    Вопрос:
    Как именно данный интерфейс interface ClubRepository связан с классом у которого есть таблица в БД?
    Ответ:
    Связывание через дженерики
    Когда вы указываете extends JpaRepository<Club, Long>, вы говорите Spring Data JPA, что этот репозиторий будет
    работать с сущностью Club, а Long — это тип ID для этой сущности. Это связывает репозиторий с классом Club и
    позволяет Spring автоматически создавать реализацию этого интерфейса.
     */
}

