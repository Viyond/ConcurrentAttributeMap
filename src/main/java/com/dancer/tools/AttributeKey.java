package com.dancer.tools;

public class AttributeKey {

    private final AttributeNameSpace nameSpace;

    private final String name;

    private final int id;

    protected AttributeKey(AttributeNameSpace nameSpace, String name){
        this.nameSpace = nameSpace;
        this.name = name;
        this.id = nameSpace.getIdGen().getAndIncrement();
    }

    public AttributeNameSpace getNameSpace() {
        return nameSpace;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
