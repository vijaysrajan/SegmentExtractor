package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Dimension;
import com.fratics.segmentextractor.json.Value;
import com.fratics.segmentextractor.util.Constants;

import java.util.HashMap;

public abstract class Processable {

    protected Context context;
    private Value currentValue = null;

    public void updateCandidates(String line) {
        String[] str = line.split(Constants.STG_SEPERATOR)[1].split(Constants.CAND_SEPERATOR);
        for (String s : str) {
            String[] cand = s.split(Constants.FIELD_SEPERATOR);
            if (!context.candidateSet.contains(cand[0])) {
                context.candidateSet.add(cand[0]);
            }
            if (!context.candidateSet.contains(cand[1])) {
                context.candidateSet.add(cand[1]);
            }
        }
    }

    public void getCurrentValue() {

        if (context.rootNode.dimensions == null) {
            currentValue = context.rootNode;
        } else {
            int i = 0;
            Dimension d = null;
            Value v = context.rootNode;
            for (String s : context.candidateSet) {
                if (i % 2 == 0)
                    d = v.dimensions.get(s);
                else
                    v = d.values.get(s);
                i++;
            }
            currentValue = v;
        }
    }

    public void addLineToJson(String line, double metric) {
        String[] str = line.split(Constants.STG_SEPERATOR)[1].split(Constants.CAND_SEPERATOR);
        for (String s : str) {
            String[] cand = s.split(Constants.FIELD_SEPERATOR);
            if (!context.candidateSet.contains(cand[0])) {
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
