package com.DFM.DFM_transfer_module.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class SqlHelper {

    // Метод для получения соединения (может быть вынесен в отдельный утилитный класс)
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres"; // замените на ваши параметры
        String username = "postgres";
        String password = "43214321";

        // Загружаем драйвер (для новых версий JDBC это не обязательно)
        // Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

}
