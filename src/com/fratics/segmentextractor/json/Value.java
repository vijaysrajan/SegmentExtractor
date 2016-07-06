package com.fratics.segmentextractor.json;

import java.util.HashMap;

public class Value {
    //public String valueName;
    public double metric = 0;
    public HashMap<String, Dimension> dimensions = null;

    public String toString() {
        return (dimensions == null) ? "null" : dimensions.keySet().toString();
    }
}
