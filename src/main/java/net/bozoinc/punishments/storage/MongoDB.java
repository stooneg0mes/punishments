package net.bozoinc.punishments.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import lombok.Getter;
import net.bozoinc.punishments.codec.PunishedUserCodec;
import net.bozoinc.punishments.codec.PunishmentCodec;
import net.bozoinc.punishments.codec.provider.GlobalCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Builder
public class MongoDB {

    private final boolean srv;
    private final String user, host, password, database;

    @Getter
    private CodecRegistry pojoCodecRegistry;

    @Getter
    private MongoDatabase mongoDatabase;

    @Getter
    private MongoClient mongoClient;

    public boolean startConnection() {
        try {
            String uri = "mongodb" + (srv ? "+srv" : "") + "://" + user + ":" + password + "@" + host + "/" + database + "?retryWrites=true&w=majority";

            pojoCodecRegistry  = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(
                  new UuidCodec(UuidRepresentation.STANDARD)
                ),
                CodecRegistries.fromProviders(
                    new GlobalCodecProvider()
                )
            );

            mongoClient = MongoClients.create(uri);
            mongoDatabase = mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);

            if (!existsCollection("punishedUsers")) mongoDatabase.createCollection("punishedUsers");

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public <T> MongoCollection<T> getCollection(String name, Class<T> clazz) {
        return mongoDatabase.getCollection(name, clazz).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean existsCollection(String name) {
        for (String string : mongoDatabase.listCollectionNames()) {
            if (string.equalsIgnoreCase(name)) return true;
        }

        return false;
    }

    public boolean isConnected() {
        return mongoClient != null;
    }

}
