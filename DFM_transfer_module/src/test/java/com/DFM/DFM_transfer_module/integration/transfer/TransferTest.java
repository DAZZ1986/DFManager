package com.DFM.DFM_transfer_module.integration.transfer;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.model.Player;
import com.DFM.DFM_transfer_module.service.ClubService;
import com.DFM.DFM_transfer_module.service.PlayerService;
import com.DFM.DFM_transfer_module.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.DFM.DFM_transfer_module.service.SqlRequest.*;
import static java.sql.DriverManager.getConnection;

//@SpringBootTest(classes = DfmTransferModuleApplication.class)
@SpringBootTest
public class TransferTest extends BaseTest {

    @Autowired // или другая аннотация для инъекции
    private ClubService clubService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TransferService transferService;



    public TransferTest() {
        // конструктор без параметров
    }

/*
    public TransferTest(ClubService clubService, PlayerService playerService, TransferService transferService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.transferService = transferService;
    }
*/
/*
1. Основной позитивный случай (минимум данных).
	Тест-кейс 1: "Успешный трансфер":
		Шаг																ТД								Ожидаемый результат
		- отправлен POST запрос с фронта на покупку игрока				игрок с id 6					отправлен POST запрос с заполненным в параметрах урл: clubId, playerId.
		- отправлен POST запрос с трансфер модуля в брокер модуль		json объект						получен ответ 200, в теле сообщения json: clubId, playerId, transferRequest true.
		- брокер модуль отправил сообщение в RabbitMQ					json объект						json сообщение поступило в очередь RabbitMQ.
		- трансфер модуль вычитал сообщение из RabbitMQ													в консоли наблюдаем сообщениме "RabbitMQ_Json_Consumer:  Received JSON message -> ...".
		- смена id клуба у игрока																		в таблице Player у записи с id 6, заполнилось поле clubId, id клуба из шага 1.
		- у клуба были вычтены деньги за игрока															в таблице Club у записи клуба бюджет клуба уменьшился на стоимость игрока. SELECT balance FROM public.club WHERE id = {clubId}
 */
/*
    //Jpa - not working
    @Test(groups = "smoke")
    @Transactional
    public void happyTransferTest() throws InterruptedException {
        // 1. Получаем данные
        Club club = clubService.findById(2L);
        Player player = playerService.findById(1L);

        //- у клуба были вычтены деньги за игрока
        int clubBalanceBeforeTransfer = club.getBalance();
        int playerPrice = player.getPrice();

        // Явно инициализируем ленивые поля ДО выполнения операций
        Hibernate.initialize(club);
        Hibernate.initialize(player);

        // 3. Выполняем операцию
        transferService.transferRequestOperation(2L, 1L);
        Thread.sleep(10000);

        // 4. Проверяем результаты
        //- смена id клуба у игрока
        //Long playerClubId = player.getClub().getId();     //jpa
        //Assert.assertEquals(playerClubId, 2);             //jpa

        //- у клуба были вычтены деньги за игрока
        int actualClubBalanceAfterTransfer = clubBalanceBeforeTransfer - playerPrice;
        Assert.assertEquals(actualClubBalanceAfterTransfer, 50505050);
    }
*/
    //JDBC - ok
    @Test(groups = "smoke")
    @Transactional
    public void happyTransferTest() throws InterruptedException {
        // 1. Получаем данные
        Club club = clubService.findById(2L);           //Real Madrid
        Player player = playerService.findById(1L);     //Zidane
        int clubBalanceBeforeTransfer = getClubBalance(club.getId(), getSqlHelper());   //вызов метода получения данных через sql запрос к БД(баланс клуба до трансфера)

        // 2. Выполняем операцию
        transferService.transferRequestOperation(club.getId(), player.getId());

        // 3. Asserts
        // 3.1 смена id клуба у игрока
        Thread.sleep(1000);         //for rabbit, consumer waiting
        //public static void assertEquals(long actual, long expected, String message)
        Assert.assertEquals(getPlayerClubId(player.getId(), getSqlHelper()), club.getId());                  //jdbc    вызов метода получения данных через sql запрос к БД

        // 3.2 у клуба были вычтены деньги за игрока
        int playerPrice = player.getPrice();
        int expectedClubBalanceAfterTransfer = clubBalanceBeforeTransfer - playerPrice;
        Assert.assertEquals(getClubBalance(club.getId(), getSqlHelper()), expectedClubBalanceAfterTransfer); //jdbc    вызов метода получения данных через sql запрос к БД
    }
}
