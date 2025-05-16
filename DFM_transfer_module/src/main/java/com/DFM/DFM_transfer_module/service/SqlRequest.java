package com.DFM.DFM_transfer_module.service;

import com.DFM.DFM_transfer_module.helpers.SqlHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlRequest {
/*
    public static String getStatusInsurance(Long insuranceId, SqlHelper sqlHelper) {
        SoftAssert softAssert = new SoftAssert();
        var insuranceStatusCodeMap = sqlHelper.queryForList(String.format(
                "SELECT DISTINCT DIC_INSURANCE_STATUS_CODE FROM RS_INSURANCE%s.INSURANCE WHERE ID = %s AND SYSMAXTXSTART is NULL", schemaRs, insuranceId), timeout);
        if (isEmpty("insuranceStatusCodeMap", insuranceStatusCodeMap, softAssert)) softAssert.assertAll();
        return (String) insuranceStatusCodeMap.get(0).get("DIC_INSURANCE_STATUS_CODE");
    }
*/

    public static int getClubBalance(Long clubIdPar, SqlHelper sqlHelper) {

        int clubBalanceBeforeTransfer = 0;
        String queryGetClubBalance = "SELECT balance FROM public.club WHERE ID = ?";
        Long clubId = clubIdPar; // пример ID

        //db conn
            try (Connection conn = sqlHelper.getConnection(); PreparedStatement pstmt = conn.prepareStatement(queryGetClubBalance)) {
            pstmt.setLong(1, clubId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String balance = rs.getString("balance");
                    clubBalanceBeforeTransfer = Integer.parseInt(balance);
                    // здесь можно добавить проверки/asserts
                } else {
                    // обработка случая, когда результат пустой
                    System.out.println("Нет данных по заданному ID");
                }
            }
            return clubBalanceBeforeTransfer;
        } catch (
        SQLException e) {
            e.printStackTrace();
            // Можно выбросить исключение или сделать ассерт неуспеха
            assert false : "Ошибка при выполнении запроса к базе данных";
            return 0;
        }
    }

    public static Long getPlayerClubId(Long playerIdPar, SqlHelper sqlHelper) {

        Long playerClubId = 0L;
        String queryGetClubBalance = "SELECT club_id FROM public.player WHERE ID = ?";
        Long playerId = playerIdPar; // пример ID

        //db conn
        try (Connection conn = sqlHelper.getConnection(); PreparedStatement pstmt = conn.prepareStatement(queryGetClubBalance)) {
            pstmt.setLong(1, playerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String clubIdStr = rs.getString("club_id");
                    playerClubId = Long.parseLong(clubIdStr);
                    // здесь можно добавить проверки/asserts
                } else {
                    // обработка случая, когда результат пустой
                    System.out.println("Нет данных по заданному ID");
                }
            }
            return playerClubId;
        } catch (
                SQLException e) {
            e.printStackTrace();
            // Можно выбросить исключение или сделать ассерт неуспеха
            assert false : "Ошибка при выполнении запроса к базе данных";
            return 0L;
        }
    }

}
