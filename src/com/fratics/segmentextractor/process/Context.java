package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Context {

    public ArrayList<String> candidateSet = new ArrayList<String>();
    private HashMap<String, Object> store = new HashMap<String, Object>();
    public Value rootNode = new Value();

    public Context() {
        //rootNode.valueName = "Root";
    }

    public void put(String s, Object o) {
        store.put(s, o);
    }

    public Object get(String s) {
        return store.get(s);
    }
}
