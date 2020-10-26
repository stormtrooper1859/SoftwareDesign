package cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    private final int totalCapacity;
    private final LRUNode<K, V> first;
    private final LRUNode<K, V> last;
    private final Map<K, LRUNode<K, V>> map;

    private int currentSize;

    public LRUCacheImpl(int capacity) {
        assert (capacity > 0);
        this.totalCapacity = capacity;
        this.currentSize = 0;

        this.map = new HashMap<>();

        this.first = new LRUNode<>(null, null);
        this.last = new LRUNode<>(null, null);
        this.first.prev = last;
        this.last.next = first;
    }

    @Override
    public void put(K key, V value) {
        int oldSize = this.currentSize;
        boolean isContain = this.contains(key);
        assert this.assertSize(oldSize);

        this.doPut(key, value);

        assert this.contains(key) && this.assertSize((isContain ? oldSize : Math.min(oldSize + 1, totalCapacity)));
    }

    private void doPut(K key, V value) {
        LRUNode<K, V> node = this.map.get(key);

        if (node != null) {
            this.removeNode(node);
        } else if (this.currentSize == this.totalCapacity) {
            node = this.last.next;
            this.removeNode(node);
            this.map.remove(node.key);
        } else {
            node = new LRUNode<>();
            this.currentSize++;
        }

        node.key = key;
        node.value = value;
        this.makeFirst(node);
        this.map.put(key, node);
    }

    @Override
    public V get(K key) {
        int oldSize = this.currentSize;
        assert this.assertSize(oldSize);

        V result = this.doGet(key);

        assert this.assertSize(oldSize);
        return result;
    }

    private V doGet(K key) {
        LRUNode<K, V> node = this.map.get(key);

        if (node == null) {
            return null;
        }

        this.removeNode(node);
        this.makeFirst(node);
        return node.value;
    }

    @Override
    public V remove(K key) {
        int oldSize = this.currentSize;
        boolean isContain = this.contains(key);
        assert this.assertSize(oldSize);

        V result = this.doRemove(key);

        assert !this.contains(key) && this.assertSize((isContain ? oldSize - 1 : oldSize));
        return result;
    }

    private V doRemove(K key) {
        LRUNode<K, V> node = this.map.get(key);

        if (node == null) {
            return null;
        }

        this.map.remove(key);
        this.removeNode(node);
        this.currentSize--;
        return node.value;
    }

    @Override
    public boolean contains(K key) {
        int oldSize = this.currentSize;
        assert this.assertSize(oldSize);

        boolean result = this.doContains(key);

        assert this.assertSize(oldSize);
        return result;
    }

    private boolean doContains(K key) {
        return this.map.containsKey(key);
    }

    private void removeNode(LRUNode<K, V> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void makeFirst(LRUNode<K, V> node) {
        node.prev = this.first.prev;
        node.next = this.first;
        this.first.prev = node;
        node.prev.next = node;
    }

    private int countNodes() {
        int i = 0;

        LRUNode<K, V> curNode = this.last;
        while (curNode.next != this.first) {
            curNode = curNode.next;
            i++;
        }

        return i;
    }

    private boolean assertSize(int size) {
        return size == this.currentSize && size == map.size() && size == this.countNodes();
    }
}
