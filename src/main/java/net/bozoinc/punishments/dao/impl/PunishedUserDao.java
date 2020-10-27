package net.bozoinc.punishments.dao.impl;

import net.bozoinc.punishments.dao.Dao;
import net.bozoinc.punishments.entity.PunishedUser;

import java.util.Collection;
import java.util.UUID;

public class PunishedUserDao implements Dao<UUID, PunishedUser> {

    private static PunishedUserDao instance;

    public static PunishedUserDao getInstance() {
        if (instance == null) instance = new PunishedUserDao();
        return instance;
    }

    @Override
    public void replace(PunishedUser value) {

    }

    @Override
    public void delete(PunishedUser value) {

    }

    @Override
    public PunishedUser find(UUID key) {
        return null;
    }

    @Override
    public Collection<PunishedUser> find() {
        return null;
    }

}
