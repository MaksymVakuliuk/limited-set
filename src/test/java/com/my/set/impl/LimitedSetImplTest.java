package com.my.set.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

public class LimitedSetImplTest {
    LimitedSetImpl<Integer> limitedSet = new LimitedSetImpl<>();

    @Before
    public void before() {
        for (int i = 0; i < 9; i++) {
            limitedSet.add(i);
        }
    }
    @After
    public void after() {
        try {
            Field field = limitedSet.getClass().getDeclaredField("map");
            field.setAccessible(true);
            HashMap hashMap = (HashMap) field.get(limitedSet);
            hashMap.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() {
        int number = 45;
        Assert.assertFalse(limitedSet.contains(number));
        limitedSet.add(number);
        Assert.assertTrue(limitedSet.contains(number));
        Integer minCheckedValue = findMinCheckedValue();
        limitedSet.add(46);
        Assert.assertTrue(limitedSet.contains(46));
        Assert.assertFalse(limitedSet.contains(minCheckedValue));
    }

    @Test
    public void remove() {
        Assert.assertTrue(limitedSet.contains(5));
        limitedSet.remove(5);
        Assert.assertFalse(limitedSet.contains(5));
    }

    @Test
    public void contains() {
        Assert.assertTrue(limitedSet.contains(4));
        Assert.assertFalse(limitedSet.contains(50));
    }

    private Integer findMinCheckedValue() {
        try {
            Method method = limitedSet.getClass().getDeclaredMethod("findMinCheckedValue");
            method.setAccessible(true);
            return (Integer) ((Optional) method.invoke(limitedSet)).get();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}