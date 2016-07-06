package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Dimension;
import com.fratics.segmentextractor.json.Value;
import com.fratics.segmentextractor.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public abstract class Processable {

    protected Context context;

    public void pruneUpdateCandidates(ArrayList<String> candidateSet, Value currentValue) {

        String highestKey = null;
        double highestValue = 0;
        Dimension d = null;
        //System.err.println("Value :: " + currentValue);
        if (currentValue.dimensions == null) return;
        Set<String> set = currentValue.dimensions.keySet();

        //Finding the Highest Key
        for (String s : set) {
            if (currentValue.dimensions.get(s).metric > highestValue) {
                highestValue = currentValue.dimensions.get(s).metric;
                d = currentValue.dimensions.get(s);
                highestKey = s;
            }
        }

        //Removing all the other keys.
        currentValue.dimensions.clear();
        currentValue.dimensions.put(highestKey, d);

        Set<String> set2 = d.values.keySet();

        for (String s : set2) {
            //Root Stage.
            if (candidateSet.isEmpty()) {
                ValueStore x = new ValueStore();
                x.candidateSet.add(highestKey);
                x.candidateSet.add(s);
                x.value = d.values.get(s);
                context.currValueStores.add(x);
            } else {
                ValueStore x = new ValueStore();
                x.candidateSet.addAll(candidateSet);
                x.candidateSet.add(highestKey);
                x.candidateSet.add(s);
                x.value = d.values.get(s);
                context.currValueStores.add(x);
            }
        }
    }


    public void addLineToJson(String line, double metric, ArrayList<String> candidateSet, Value currentValue) {
        String[] str = line.split(Constants.STG_SEPERATOR)[1].split(Constants.CAND_SEPERATOR);
        for (String s : str) {
            String[] cand = s.split(Constants.FIELD_SEPERATOR);
            if (!candidateSet.contains(cand[0])) {
                Value v = new Value();
                //v.valueName = cand[1];
                v.metric = metric;
                Dimension d = null;
                if (currentValue.dimensions == null) currentValue.dimensions = new HashMap<String, Dimension>();
                if (currentValue.dimensions.containsKey(cand[0])) {
                    d = currentValue.dimensions.get(cand[0]);
                } else {
                    d = new Dimension();
                    d.values = new HashMap<String, Value>();
                    //d.dimName = cand[0];
                    currentValue.dimensions.put(cand[0], d);
                }
                d.metric += metric;
                d.values.put(cand[1], v);
            }
        }
    }

    public abstract void doProcess();

    public void initialize() {
    }

    public void unInitialize() {
    }

    public void reInitialize() {
        this.unInitialize();
        this.initialize();
    }
}
