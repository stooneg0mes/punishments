package net.bozoinc.punishments.dao;

import java.util.Collection;

public interface Dao<K, V> {

    void replace(V value);

    void delete(V value);

    V find(K key);

    Collection<V> find();

}
