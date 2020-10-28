package net.bozoinc.punishments.storage;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import lombok.Getter;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

@Builder
public class MongoDB {

    private final boolean srv;
    private final String user, host, password, database;

    @Getter
    private final CodecRegistry pojoCodecRegistry;

    @Getter
    private MongoDatabase mongoDatabase;

    @Getter
    private MongoClient mongoClient;

    public boolean startConnection() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        String uri = "mongodb" + (srv ? "+srv" : "") + "://" + user + ":" + password + "@" + host + "/" + database + "?retryWrites=true&w=majority";

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        mongoClient = MongoClients.create(uri);
        mongoDatabase = mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);

        if (!existsCollection("punishedUsers")) mongoDatabase.createCollection("punishedUsers");

        return mongoClient.startSession().hasActiveTransaction();
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
