package com.dancer.tools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AttributeNameSpace {

    private static final ConcurrentHashMap<String, AttributeNameSpace> name2Instance = new ConcurrentHashMap<>();

    public static AttributeNameSpace getOrCreateNameSpace(String namespace){
        AttributeNameSpace instance = name2Instance.get(namespace);
        if (null == instance){
            AttributeNameSpace tmp = new AttributeNameSpace(namespace);
            AttributeNameSpace prev = name2Instance.putIfAbsent(namespace, tmp);
            if (null == prev){
                instance = tmp;
            }else {
                instance = prev;
            }
        }
        return instance;
    }

    private final String namespace;

    private final AtomicInteger idGen = new AtomicInteger(0);

    private final ConcurrentHashMap<String, AttributeKey> name2AttributeKey = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Integer, AttributeKey> id2AttributeKey = new ConcurrentHashMap<>();

    protected AttributeNameSpace(String namespace){
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public AtomicInteger getIdGen() {
        return idGen;
    }

    public AttributeKey getOrCreate(String name){
        AttributeKey key = name2AttributeKey.get(name);
        if (null == key){
            key = new AttributeKey(this, name);
            AttributeKey prev = name2AttributeKey.putIfAbsent(name, key);
            if (null == prev){
                cacheById(key);
            }else {
                key = prev;
            }
        }
        return key;
    }

    public AttributeKey create(String name){
        AttributeKey key = new AttributeKey(this, name);
        AttributeKey prev = name2AttributeKey.putIfAbsent(name, key);
        if (null != prev){
            throw new IllegalArgumentException(String.format("name '%s' in namespace '%s' is already exist.", name, namespace));
        }
        cacheById(key);
        return key;
    }

    private void cacheById(AttributeKey attributeKey){
        id2AttributeKey.putIfAbsent(attributeKey.getId(), attributeKey);
    }
}
