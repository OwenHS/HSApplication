package com.hs.lib.processannotation.core.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by owen on 16-1-4.
 * 用于缓存create出来的对象
 *
 */
public abstract class Memoizer<K, V> {
    private final Map<K, V> map;
    private final Lock readLock;
    private final Lock writeLock;

    public Memoizer() {
        this.map = new LinkedHashMap<K, V>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    public final V get(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }

        // check to see if we already have a value
        readLock.lock();
        try {
            V value = map.get(key);
            if (value != null) {
                return value;
            }
        } finally {
            readLock.unlock();
        }

        // create a new value.  this may race and we might create more than one instance, but that's ok
        V newValue = create(key);
        if (newValue == null) {
            throw new NullPointerException("create returned null");
        }

        // write the new value and return it
        writeLock.lock();
        try {
            map.put(key, newValue);
            return newValue;
        } finally {
            writeLock.unlock();
        }
    }

    protected abstract V create(K key);

    @Override
    public final String toString() {
        readLock.lock();
        try {
            return map.toString();
        } finally {
            readLock.unlock();
        }
    }
}