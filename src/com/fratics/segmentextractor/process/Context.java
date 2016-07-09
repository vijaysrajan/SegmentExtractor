package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Context {

    public ArrayList<ValueStore> prevValueStores = new ArrayList<ValueStore>();
    public ArrayList<ValueStore> currValueStores = new ArrayList<ValueStore>();
    public Value rootNode = new Value();
    private HashMap<String, String> store = new HashMap<String, String>();

    public Context() {
        //rootNode.valueName = "Root";
    }

    public void incrStage() {
        prevValueStores = currValueStores;
        currValueStores = new ArrayList<ValueStore>();
    }

    public void put(String s, String o) {
        store.put(s, o);
    }

    public String get(String s) {
        return store.get(s);
    }
}
