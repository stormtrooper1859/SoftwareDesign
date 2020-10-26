package cache;

class LRUNode<K, V> {

    LRUNode<K, V> next;
    LRUNode<K, V> prev;
    K key;
    V value;

    LRUNode() { }

    LRUNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
