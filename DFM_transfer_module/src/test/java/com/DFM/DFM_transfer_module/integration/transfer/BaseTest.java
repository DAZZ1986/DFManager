package com.DFM.DFM_transfer_module.integration.transfer;

import com.DFM.DFM_transfer_module.helpers.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

@SpringBootTest
public class BaseTest extends AbstractTestNGSpringContextTests {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - выполняется один раз перед всеми тестами в наборе");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - выполняется перед тестами из <test> в testng.xml");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - выполняется перед первым тестом в классе");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - выполняется перед каждым тестовым методом");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - выполняется после каждого тестового метода");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - выполняется после всех тестов в классе");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - выполняется после тестов из <test> в testng.xml");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - выполняется один раз после всех тестов в наборе");
    }


/*
    Тут BaseTest создал объект класса SqlHelper и затянул к себе и далее у наследников класса BaseTest н/п: TransferTest extends BaseTest
    появляется возможность пользоваться тем что есть у BaseTest.
    int clubBalanceBeforeTransfer = getClubBalance(club.getId(), getSqlHelper()); - в классе TransferTest я закинул
    переменную sqlHelper в параметр метода clubBalanceBeforeTransfer через геттер getSqlHelper()
    и далее просто в этом методе через переменную в параметре sqlHelper я дотягиваюсь до метода класса SqlHelper -> getConnection().
*/
    @Autowired
    public SqlHelper sqlHelper;

    public SqlHelper getSqlHelper() {
        return sqlHelper;
    }

}
