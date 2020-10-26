package cache;

public interface LRUCache<K, V> {
    void put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean contains(K key);
}
