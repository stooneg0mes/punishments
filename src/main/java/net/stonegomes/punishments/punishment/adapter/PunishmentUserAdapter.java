package net.stonegomes.punishments.punishment.adapter;

import lombok.Getter;
import net.stonegomes.commons.serializer.ListSerializer;
import net.stonegomes.commons.storage.SqlAdapter;
import net.stonegomes.commons.storage.query.Query;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.serializer.PunishmentSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PunishmentUserAdapter implements SqlAdapter<PunishmentUser> {

    @Getter
    private static final PunishmentUserAdapter instance = new PunishmentUserAdapter();

    private final ListSerializer<Punishment> punishmentListSerializer = new ListSerializer<>(PunishmentSerializer.class);

    @Override
    public PunishmentUser read(ResultSet resultSet) {
        try {
            return PunishmentUser.builder()
                .bypass(resultSet.getBoolean("bypass"))
                .uniqueId(UUID.fromString(resultSet.getString("uniqueId")))
                .punishments(punishmentListSerializer.deserialize(resultSet.getString("punishments"), ";"))
                .build();
        } catch (SQLException ignored) {
            return null;
        }
    }

    @Override
    public Query buildReplaceQuery(PunishmentUser value) {
        return Query.builder()
            .query("REPLACE INTO punishmentUsers (bypass, uniqueId, punishments) VALUES (?, ?, ?)")
            .values(
                value.isBypass(),
                value.getUniqueId().toString(),
                punishmentListSerializer.serialize(value.getPunishments(), ";")
            )
            .build();
    }

    @Override
    public Query buildDeleteQuery(Object object) {
        return Query.builder()
            .query("DELETE FROM punishmentUsers WHERE uniqueId = ?")
            .values(object)
            .build();
    }

}
