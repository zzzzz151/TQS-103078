package tqs.airqualityapp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Cache<K, V> {
    private final Map<K, CacheEntry<V>> cache;
    private final long timeToLive;
    private long requests;
    private long hits;
    private long misses;

    public Cache(long timeToLiveInSeconds) {
        this.cache = new HashMap<>();
        this.timeToLive = timeToLiveInSeconds * 1000; // Convert seconds to milliseconds
        this.requests = 0;
        this.hits = 0;
        this.misses = 0;

        // Start a background thread to periodically remove expired entries from the
        // cache
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1 / 10);
                    cleanup();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // restore the interrupted status
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    public synchronized V get(K key) {
        requests++;

        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            if (!entry.isExpired()) {
                hits++;
                entry.setAccessTime(System.currentTimeMillis());
                entry.resetCreationTime(); // refresh TTL
                Utils.log("Hit key " + key + " in cache");
                return entry.getValue();
            } else {
                cache.remove(key);
            }
        }
        Utils.log("Missed key " + key + " in cache");
        misses++;
        return null;
    }

    public synchronized void put(K key, V value) {
        cache.put(key, new CacheEntry<>(value, timeToLive));
        Utils.log("Put key " + key + " in cache");
    }

    private synchronized void cleanup() {
        Set<K> expiredKeys = new HashSet<K>();
        for (K key : cache.keySet())
            if (cache.get(key).isExpired())
                expiredKeys.add(key);
        for (K expiredKey : expiredKeys) {
            cache.remove(expiredKey);
            Utils.log("Removed key " + expiredKey + " from cache");
        }

        // cache.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
    }

    public synchronized long getRequests() {
        return requests;
    }

    public synchronized long getHits() {
        return hits;
    }

    public synchronized long getMisses() {
        return misses;
    }

    public long getTimeToLiveSeconds() {
        return timeToLive / 1000;
    }

    private static class CacheEntry<V> {
        private final V value;
        private long creationTime;
        private long accessTime;
        private final long timeToLive;

        public CacheEntry(V value, long timeToLive) {
            this.value = value;
            this.creationTime = System.currentTimeMillis();
            this.accessTime = creationTime;
            this.timeToLive = timeToLive;
        }

        public V getValue() {
            return value;
        }

        public long getCreationTime() {
            return creationTime;
        }

        public void resetCreationTime() {
            this.creationTime = System.currentTimeMillis();
        }

        public long getAccessTime() {
            return accessTime;
        }

        public void setAccessTime(long accessTime) {
            this.accessTime = accessTime;
        }

        public boolean isExpired() {
            return isExpired(System.currentTimeMillis());
        }

        public boolean isExpired(long now) {
            return now - creationTime > timeToLive;
        }
    }
}