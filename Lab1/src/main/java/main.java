import cache.LRUCache;
import cache.LRUCacheImpl;

public class main {

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCacheImpl<>(2);

        cache.put("kek", "lol");
        cache.put("cho", "to");
        cache.put("ab", "cd");

        cache.remove("cho");
        cache.remove("kek");
        cache.put("kek", "lol2");
    }

}
