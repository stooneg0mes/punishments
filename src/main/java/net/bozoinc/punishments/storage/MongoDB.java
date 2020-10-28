package net.bozoinc.punishments.storage;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {

    private MongoClient mongoClient;
    private CodecRegistry pojoCodecRegistry;

    private final String host, database;
    private final int port;

    public MongoDB(String host, String database, int port) {
        this.host = host;
        this.database = database;
        this.port = port;
    }

    public boolean startConnection() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        try {
            pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
                .codecRegistry(pojoCodecRegistry)
                .build());

            if (!existsCollection("punishedUsers")) getDatabase().createCollection("punishedUsers");

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean existsCollection(String collection) {
        for (String string : getDatabase().listCollectionNames()) {
            if (string.equalsIgnoreCase(collection)) return true;
        }

        return false;
    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);
    }

    public <T> MongoCollection<T> getCollection(String collection, Class<T> clazz) {
        return getDatabase().getCollection(collection, clazz).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean isConnected() {
        return mongoClient != null;
    }

}
