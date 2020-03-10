package com.dancer.tools;

public interface AttributeMap {

    Object put(AttributeKey key, Object value);

    Object get(AttributeKey key);

    Object putIfAbsent(AttributeKey key, Object value);

    Object remove(AttributeKey key);
}
