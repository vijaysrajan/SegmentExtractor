package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Dimension;

public class UpdateRootMetric extends Processable{

    public UpdateRootMetric(Context context){
        this.context = context;
    }

    public void doProcess(){
        double value = 0.0;
        for (Dimension d : context.rootNode.dimensions.values()){
            value += d.metric;
        }
        context.rootNode.metric = value;
    }
}
