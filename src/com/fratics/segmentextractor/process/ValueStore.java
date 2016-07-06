package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Value;

import java.util.ArrayList;

public class ValueStore {
    public ArrayList<String> candidateSet = new ArrayList<String>();
    public Value value;

    public String toString() {
        return candidateSet.toString() + ":" + value;
    }
}