package com.dancer.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ConcurrentAttributeMapTest {

    private static final AttributeNameSpace myNameSpace = AttributeNameSpace.getOrCreateNameSpace("my_namespace");
    private static final AttributeKey attribute_1 = myNameSpace.create("attribute_1");
    private static final AttributeKey attribute_2 = myNameSpace.create("attribute_2");

    private final ConcurrentAttributeMap attributeMap = new ConcurrentAttributeMap(myNameSpace, 10);

    @Test
    public void putAndGet(){
        try{
            ExecutorService executor = Executors.newCachedThreadPool();
            Future<InnerDef> f1 = executor.submit(new InnerCallable(attribute_1));
            Future<InnerDef> f2 = executor.submit(new InnerCallable(attribute_2));

            System.out.println(f1.get());
            System.out.println(f2.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class InnerCallable implements Callable<InnerDef>{
        private final AttributeKey attributeKey;
        public InnerCallable(final AttributeKey attributeKey) {
            this.attributeKey = attributeKey;
        }

        @Override
        public InnerDef call() throws Exception {
            InnerDef temp = new InnerDef();
            attributeMap.putIfAbsent(attributeKey, temp);
            InnerDef value = (InnerDef)attributeMap.get(attributeKey);
            return value;
        }
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
