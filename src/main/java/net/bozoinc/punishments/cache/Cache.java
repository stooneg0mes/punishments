package net.bozoinc.punishments.cache;


import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class Cache<K, V> {

    @Getter
    private final Map<K, V> elements = new WeakHashMap<>();

    public void put(K key, V value) {
        elements.put(key, value);
    }

    public V remove(K key) {
        return elements.remove(key);
    }

    public V get(K key) {
        return elements.get(key);
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public Collection<V> values() {
        return elements.values();
    }

    public Collection<K> keys() {
        return elements.keySet();
    }

    /*
    Abstract
     */

    public abstract void load();

    public abstract void save();

}
