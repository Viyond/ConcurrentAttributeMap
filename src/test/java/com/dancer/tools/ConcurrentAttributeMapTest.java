package com.dancer.tools;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ConcurrentAttributeMapTest {

    private static final AttributeNameSpace myNameSpace = AttributeNameSpace.getOrCreateNameSpace("my_namespace");
    private static final AttributeKey attribute_1 = myNameSpace.create("attribute_1");

    private ConcurrentAttributeMap attributeMap = new ConcurrentAttributeMap(myNameSpace, 10);

    @Test
    public void putAndGet(){
        InnerDef temp = new InnerDef();
        attributeMap.put(attribute_1, temp);
        InnerDef value = (InnerDef)attributeMap.get(attribute_1);
        System.out.println(temp == value);
    }

    private static class InnerDef{
        private static final AtomicInteger idGen = new AtomicInteger(0);
        private final int id;

        public InnerDef(){
            id = idGen.getAndIncrement();
        }

        @Override
        public String toString() {
            return "InnerDef{" +
                "id=" + id +
                '}';
        }
    };
}
