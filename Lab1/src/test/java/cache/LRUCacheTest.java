package cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheTest {

    @Test
    public void testOverflow() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("key1", "data1");
        cache.put("key2", "data2");

        assertTrue(cache.contains("key1"));

        cache.put("key3", "data3");

        assertFalse(cache.contains("key1"));
        assertTrue(cache.contains("key2"));
        assertTrue(cache.contains("key3"));
    }

    @Test
    public void testOverflowLeastUsed() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(3);

        cache.put("key1", "data1");
        cache.put("key2", "data2");
        cache.put("key3", "data3");

        cache.get("key2");
        cache.get("key3");
        cache.get("key1");
        cache.get("key2");

        cache.put("key4", "data4");

        assertTrue(cache.contains("key1"));
        assertTrue(cache.contains("key2"));
        assertTrue(cache.contains("key4"));
        assertFalse(cache.contains("key3"));
    }

    @Test
    public void testSetValueTwice() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("key1", "data1");
        cache.put("key2", "data2");

        cache.put("key1", "data3");

        assertEquals(cache.get("key1"), "data3");
        assertEquals(cache.get("key2"), "data2");
    }

    @Test
    public void testSetValueTwiceAndOverflowLeastUsed() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("key1", "data1");
        cache.put("key2", "data2");

        cache.put("key1", "data3");
        cache.put("key4", "data4");

        assertEquals("data3", cache.get("key1"));
        assertFalse(cache.contains("key2"));
        assertTrue(cache.contains("key4"));
    }

    @Test
    public void testRemove() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("key1", "data1");
        cache.put("key2", "data2");

        cache.remove("key1");

        cache.put("key3", "data3");

        assertFalse(cache.contains("key1"));
        assertTrue(cache.contains("key2"));
        assertTrue(cache.contains("key3"));
    }

    @Test
    public void testRemoveAndOverflow() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(3);

        cache.put("key1", "data1");
        cache.put("key2", "data2");
        cache.put("key3", "data3");

        cache.remove("key3");

        cache.put("key4", "data4");
        cache.put("key5", "data5");

        assertFalse(cache.contains("key1"));
        assertTrue(cache.contains("key2"));
        assertFalse(cache.contains("key3"));
        assertTrue(cache.contains("key4"));
        assertTrue(cache.contains("key5"));
    }

    @Test
    public void testPutNull() {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("key1", "data1");
        cache.put("key2", null);

        assertTrue(cache.contains("key1"));
        assertTrue(cache.contains("key2"));
        assertNull(cache.get("key2"));
    }
}