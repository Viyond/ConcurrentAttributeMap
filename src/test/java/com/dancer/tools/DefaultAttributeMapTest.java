package com.dancer.tools;

import org.junit.Assert;
import org.junit.Test;

public class DefaultAttributeMapTest {
    private static final AttributeNameSpace myNameSpace = AttributeNameSpace.getOrCreateNameSpace("my_namespace");
    private static final AttributeKey attribute_1 = myNameSpace.create("attribute_1");

    private final AttributeMap attributeMap = new DefaultAttributeMap(myNameSpace, 10);

    @Test
    public void putAndGet(){
        Object tmp = new Object();
        attributeMap.put(attribute_1, tmp);
        Assert.assertTrue(tmp == attributeMap.get(attribute_1));
    }
}
