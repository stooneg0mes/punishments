package net.bozoinc.punishments.dao.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.bozoinc.punishments.PunishmentPlugin;
import net.bozoinc.punishments.dao.Dao;
import net.bozoinc.punishments.entity.PunishedUser;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class PunishedUserDao implements Dao<UUID, PunishedUser> {

    private static PunishedUserDao instance;

    public static PunishedUserDao getInstance() {
        if (instance == null) instance = new PunishedUserDao();
        return instance;
    }

    private final MongoCollection<PunishedUser> mongoCollection = PunishmentPlugin.getInstance().getMongoDB().getCollection("punishedUsers", PunishedUser.class);

    @Override
    public void replace(PunishedUser value) {
        if (find(value.getUuid()) == null) {
            mongoCollection.insertOne(value);
        } else mongoCollection.replaceOne(eq("uuid"), value);
    }

    @Override
    public void delete(PunishedUser value) {
        mongoCollection.deleteOne(eq("uuid", value.getUuid()));
    }

    @Override
    public PunishedUser find(UUID key) {
        return mongoCollection.find(eq("uuid", key)).first();
    }

    @Override
    public FindIterable<PunishedUser> find() {
        return mongoCollection.find();
    }

}
