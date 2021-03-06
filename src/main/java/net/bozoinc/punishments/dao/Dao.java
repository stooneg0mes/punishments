package net.bozoinc.punishments.dao;

import com.mongodb.client.FindIterable;

public interface Dao<K, V> {

    void replace(V value);

    void delete(V value);

    V find(K key);

    FindIterable<V> find();

}
