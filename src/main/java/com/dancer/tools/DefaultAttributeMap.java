package com.dancer.tools;

import java.util.Arrays;

/**
 * 非线程安全的实现
 */
public class DefaultAttributeMap implements AttributeMap{

    private final AttributeNameSpace nameSpace;

    /**
     * 元素之间允许出现空洞和不连续
     */
    private volatile Object[] values;

    public DefaultAttributeMap(AttributeNameSpace nameSpace, int size){
        this.nameSpace = nameSpace;
        values = new Object[size];
    }

    @Override
    public Object put(AttributeKey key, Object value) {
        Object[] elements = values;
        int id = key.getId();
        if (id >= elements.length){
            elements = Arrays.copyOf(elements, elements.length + 1);
            values = elements;
        }
        Object prev = elements[id];
        elements[id] = value;
        return prev;
    }

    @Override
    public Object get(AttributeKey key) {
        int id = key.getId();
        if (id >= values.length){
            return null;
        }else {
            return values[id];
        }
    }

    @Override
    public Object putIfAbsent(AttributeKey key, Object value) {
        throw new UnsupportedOperationException("putIfAbsent not implement yet!");
    }

    @Override
    public Object remove(AttributeKey key) {
        int id = key.getId();
        if (id >= values.length){
            return null;
        }else {
            Object old = values[id];
            values[id] = null;
            return old;
        }
    }
}
