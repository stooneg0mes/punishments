package net.stonegomes.punishments.punishment.cache;

import lombok.Getter;
import net.stonegomes.commons.cache.DoubleCache;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.dao.PunishmentUserDao;

import java.util.UUID;

public class PunishmentUserCache extends DoubleCache<UUID, PunishmentUser> {

    @Getter
    private static final PunishmentUserCache instance = new PunishmentUserCache();


    private final PunishmentUserDao punishmentUserDao = PunishmentUserDao.getInstance();

    public void load() {
        for (PunishmentUser punishmentUser : punishmentUserDao.find()) {
            putElement(punishmentUser.getUniqueId(), punishmentUser);
        }
    }

    public void save() {
        for (PunishmentUser punishmentUser : getValues()) {
            punishmentUserDao.replace(punishmentUser);
        }
    }

}
