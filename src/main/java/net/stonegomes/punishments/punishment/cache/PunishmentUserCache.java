package net.stonegomes.punishments.punishment.cache;

import lombok.Getter;
import net.stonegomes.commons.cache.DoubleCache;
import net.stonegomes.punishments.punishment.PunishmentUser;

import java.util.UUID;

public class PunishmentUserCache extends DoubleCache<UUID, PunishmentUser> {

    @Getter
    private static final PunishmentUserCache instance = new PunishmentUserCache();

    public void load() {
        /*
        TODO
         */
    }

    public void save() {
        /*
        TODO
         */
    }

}
