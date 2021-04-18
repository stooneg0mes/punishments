package net.stonegomes.punishments.punishment.dao;

import lombok.Getter;
import net.stonegomes.commons.dao.Dao;
import net.stonegomes.punishments.punishment.PunishmentUser;

import java.util.Collection;
import java.util.UUID;

public class PunishmentUserDao implements Dao<UUID, PunishmentUser> {

    @Getter
    private final PunishmentUserDao instance = new PunishmentUserDao();

    @Override
    public void replace(PunishmentUser value) {
        /*
        TODO
         */
    }

    @Override
    public void delete(UUID key) {
        /*
        TODO
         */
    }

    @Override
    public PunishmentUser find(UUID key) {
        return null;
    }

    @Override
    public Collection<PunishmentUser> find() {
        return null;
    }

}
