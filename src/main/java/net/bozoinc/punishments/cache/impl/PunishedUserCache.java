package net.bozoinc.punishments.cache.impl;

import net.bozoinc.punishments.cache.Cache;
import net.bozoinc.punishments.dao.impl.PunishedUserDao;
import net.bozoinc.punishments.entity.PunishedUser;

import java.util.UUID;

public class PunishedUserCache extends Cache<UUID, PunishedUser> {

    private static PunishedUserCache instance;

    public static PunishedUserCache getInstance() {
        if (instance == null) instance = new PunishedUserCache();
        return instance;
    }

    private final PunishedUserDao punishedUserDao = PunishedUserDao.getInstance();

    @Override
    public void load() {
        for (PunishedUser punishedUser : punishedUserDao.find()) {
            put(punishedUser.getUuid(), punishedUser);
        }
    }

    @Override
    public void save() {
        for (PunishedUser punishedUser : values()) {
            punishedUserDao.replace(punishedUser);
        }
    }

}
