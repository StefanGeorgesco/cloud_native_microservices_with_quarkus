package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiTable;

public class TablesMapper {

    private TablesMapper() {
    }

    public static Table toEntity(ApiTable apiTable) {
        final Table table = new Table();
        table.setName(apiTable.getName());
        table.setSeatCount(apiTable.getSeatCount());
        table.setActive(apiTable.getActive());
        return table;
    }

    public static ApiTable toApi(Table table) {
        final ApiTable apiTable = new ApiTable();
        apiTable.setId(table.getId());
        apiTable.setName(table.getName());
        apiTable.setSeatCount(table.getSeatCount());
        apiTable.setActive(table.getActive());
        return apiTable;
    }

    public static void updateEntity(Table table, ApiTable apiTable) {
        table.setName(apiTable.getName());
        table.setSeatCount(apiTable.getSeatCount());
        table.setActive(apiTable.getActive());
    }
}
