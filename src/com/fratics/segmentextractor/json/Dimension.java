package com.fratics.segmentextractor.json;

import java.util.HashMap;

public class Dimension {
    //public String dimName;
    public double metric = 0;
    public HashMap<String, Value> values = null;

    public String toString() {
        return (values == null) ? "null" : values.keySet().toString();
    }
}
