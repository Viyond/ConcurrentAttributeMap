package com.dancer.tools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConcurrentAttributeMap implements AttributeMap{

    private final AttributeNameSpace nameSpace;

    private final int size;

    private final AtomicReferenceArray<Object> values;

    private volatile ConcurrentHashMap<Integer, Object> backupMap;

    public ConcurrentAttributeMap(AttributeNameSpace nameSpace, int size){
        this.nameSpace = nameSpace;
        this.size = size;
        values = new AtomicReferenceArray<>(size);
    }

    @Override
    public Object put(AttributeKey key, Object value) {
        int id = key.getId();
        if (id >= size){
            return getBackupMap().put(id, value);
        }else {
            return values.getAndSet(id, value);
        }
    }

    @Override
    public Object get(AttributeKey key) {
        int id = key.getId();
        if (id >= size){
            return getBackupMap().get(key);
        }else {
            return values.get(id);
        }
    }

    @Override
    public Object putIfAbsent(AttributeKey key, Object value) {
        int id = key.getId();
        if (id >= size){
            return getBackupMap().putIfAbsent(id, value);
        }else {
            boolean absent = values.compareAndSet(id, null, value);
            if (absent){
                return null;
            }else {
                return values.get(id);
            }
        }
    }

    @Override
    public Object remove(AttributeKey key) {
        int id = key.getId();
        if (id >= size){
            return getBackupMap().remove(id);
        }else {
            return values.getAndSet(id, null);
        }
    }

    private ConcurrentHashMap<Integer, Object> getBackupMap(){
        if (null == backupMap){
            synchronized (this){
                if (null == backupMap){
                    backupMap = new ConcurrentHashMap<>();
                }
            }
        }
        return backupMap;
    }
}
