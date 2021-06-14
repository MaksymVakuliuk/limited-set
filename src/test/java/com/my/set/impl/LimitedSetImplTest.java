package com.my.set.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class LimitedSetImplTest {
    LimitedSetImpl<Integer> limitedSet = new LimitedSetImpl<>();

    @Before
    public void before() {
        for (int i = 1; i < 9; i++) {
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
    }


    @Test
    public void addExistedValueToFullSet() {
        limitedSet.add(9);
        limitedSet.add(10);
        limitedSet.contains(2);
        Integer numberOfContains = getNumberOfContains(2);
        limitedSet.add(2);
        Assert.assertEquals(numberOfContains, getNumberOfContains(2));
    }


    @Test
    public void addWithOverSize() {
        Integer minCheckedValue = findMinCheckedValue();
        limitedSet.add(46);
        limitedSet.add(47);
        Assert.assertTrue(limitedSet.contains(minCheckedValue));
        minCheckedValue = findMinCheckedValue();
        limitedSet.add(38);
        Assert.assertFalse(limitedSet.contains(minCheckedValue));
        Assert.assertTrue(limitedSet.contains(38));
        Assert.assertTrue(limitedSet.contains(46));

    }

    @Test
    public void addExistedValue() {
        limitedSet.add(33);
        limitedSet.contains(33);
        limitedSet.contains(33);
        Integer numberOfContains = getNumberOfContains(33);
        limitedSet.add(33);
        Assert.assertEquals(numberOfContains,getNumberOfContains(33));
    }


    @Test
    public void addNull() {
        limitedSet.add(null);
        Assert.assertFalse(limitedSet.contains(null));
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

    private <T> Integer getNumberOfContains(T t) {
        try {
            Field field  = limitedSet.getClass().getDeclaredField("map");
            field.setAccessible(true);
            HashMap<T, Integer> hashMap = (HashMap<T, Integer>) field.get(limitedSet);
            return hashMap.get(t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Integer findMinCheckedValue() {
        try {
            Method method = limitedSet.getClass().getDeclaredMethod("findMinCheckedValue");
            method.setAccessible(true);
            return (Integer) method.invoke(limitedSet);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
