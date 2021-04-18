package net.stonegomes.punishments.punishment.dao;

import lombok.Getter;
import net.stonegomes.commons.dao.Dao;
import net.stonegomes.commons.storage.SqlStorage;
import net.stonegomes.commons.storage.query.Query;
import net.stonegomes.punishments.module.StorageModule;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.adapter.PunishmentUserAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PunishmentUserDao implements Dao<UUID, PunishmentUser> {

    @Getter
    private final PunishmentUserDao instance = new PunishmentUserDao();

    private final SqlStorage sqlStorage = StorageModule.getSqlStorage();
    private final PunishmentUserAdapter punishmentUserAdapter = PunishmentUserAdapter.getInstance();

    @Override
    public void replace(PunishmentUser value) {
        Query replaceQuery = punishmentUserAdapter.buildReplaceQuery(value);
        sqlStorage.executeQuery(replaceQuery);
    }

    @Override
    public void delete(UUID key) {
        Query deleteQuery = punishmentUserAdapter.buildDeleteQuery(key);
        sqlStorage.executeQuery(deleteQuery);
    }

    @Override
    public PunishmentUser find(UUID key) {
        Query findQuery = Query.builder()
            .query("SELECT * FROM punishmentUsers WHERE uniqueId = ?")
            .values(key.toString())
            .build();

        ResultSet resultSet = sqlStorage.executeQuery(findQuery);
        return punishmentUserAdapter.read(resultSet);
    }

    @Override
    public Collection<PunishmentUser> find() {
        Set<PunishmentUser> punishmentUsers = new HashSet<>();

        try {
            Query findQuery = Query.builder()
                .query("SELECT * FROM punishmentUsers")
                .build();

            ResultSet resultSet = sqlStorage.executeQuery(findQuery);
            while (resultSet.next()) {
                PunishmentUser punishmentUser = punishmentUserAdapter.read(resultSet);
                punishmentUsers.add(punishmentUser);
            }

            return punishmentUsers;
        } catch (SQLException ignored) {
            return punishmentUsers;
        }
    }

}
