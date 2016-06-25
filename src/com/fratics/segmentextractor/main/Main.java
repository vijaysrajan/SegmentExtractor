package com.fratics.segmentextractor.main;

import com.fratics.segmentextractor.json.GenerateJson;
import com.fratics.segmentextractor.process.Context;
import com.fratics.segmentextractor.process.SegmentProcessor;
import com.fratics.segmentextractor.process.UpdateRootMetric;
import com.fratics.segmentextractor.util.Constants;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        long milliSec1 = new Date().getTime();
        Context context = new Context();
        /*
        if (args.length < 1) {
            //new AutoDetectDimension(context).doProcess();
        } else {
            context.candidateSet.add(args[0]);
        }
        */
        new SegmentProcessor(context, Constants.STG_1_FILE).doProcess();
        new SegmentProcessor(context, Constants.STG_2_FILE).doProcess();
        new SegmentProcessor(context, Constants.STG_3_FILE).doProcess();
        new SegmentProcessor(context, Constants.STG_4_FILE).doProcess();
        new UpdateRootMetric(context).doProcess();
        new GenerateJson(context).doProcess();
        long milliSec2 = new Date().getTime();
        System.err.println("Completed Json Generation in :" + (milliSec2 - milliSec1) + "ms ..Check Result in Data Folder");
    }
}
