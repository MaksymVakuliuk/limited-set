package com.my.set.impl;

import com.my.set.LimitedSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LimitedSetImpl<T> implements LimitedSet<T> {
    private static final int MAX_SIZE = 10;
    private final Map<T, Integer> map = new HashMap<>();

    @Override
    public void add(T t) {
        if (t == null) {
            return;
        }
        if (!map.containsKey(t)) {
            if (map.size() >= MAX_SIZE) {
                map.remove(findMinCheckedValue());
            }
            map.put(t, 0);
        }
    }

    @Override
    public boolean remove(T t) {
        return map.remove(t) != null;
    }

    @Override
    public boolean contains(T t) {
        if (map.containsKey(t)) {
            map.put(t, map.get(t) + 1);
            return true;
        }
        return false;
    }

    private T findMinCheckedValue() {
        return map.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .get();
    }
}
