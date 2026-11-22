package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.api.TablesApi;
import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiTable;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

@Transactional
public class TablesResource implements TablesApi {

    private final TablesCrudService tablesCrudService;

    public TablesResource(TablesCrudService tablesCrudService) {
        this.tablesCrudService = tablesCrudService;
    }

    @Override
    public Response tablesTableIdDelete(Long tableId) {
        final Optional<Table> table = tablesCrudService.deleteById(tableId);
        if (table.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @Override
    public Response tablesTableIdGet(Long tableId) {
        final Optional<Table> table = tablesCrudService.getById(tableId);
        if (table.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((TablesMapper.toApi(table.get()))).build();
    }

    @Override
    public Response tablesTableIdPut(Long tableId, ApiTable apiTable) {
        final Optional<Table> existingTable = tablesCrudService.getById(tableId);
        if (existingTable.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Table table = existingTable.get();
        TablesMapper.updateEntity(table, apiTable);
        tablesCrudService.update(table);
        return Response.ok().build();
    }

    @Override
    public Response tablesGet() {
        return Response.ok(tablesCrudService.getAll()
                        .stream()
                        .map(TablesMapper::toApi)
                        .toList())
                .build();
    }

    @Override
    public Response tablesPost(ApiTable apiTable) {
        final Table table = TablesMapper.toEntity(apiTable);
        final Table persistedTable = tablesCrudService.persist(table);
        return Response.created(URI.create("/tables/" + persistedTable.getId())).build();
    }
}
