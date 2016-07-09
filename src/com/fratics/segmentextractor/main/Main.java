package com.fratics.segmentextractor.main;

import com.fratics.segmentextractor.json.GenerateJson;
import com.fratics.segmentextractor.process.Context;
import com.fratics.segmentextractor.process.SegmentProcessor1stStage;
import com.fratics.segmentextractor.process.SegmentProcessorStage2Onwards;
import com.fratics.segmentextractor.process.UpdateRootMetric;
import com.fratics.segmentextractor.util.Constants;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        long milliSec1 = new Date().getTime();
        Context context = new Context();
        new SegmentProcessor1stStage(context, Constants.STG_1_FILE, 1).doProcess();
        new SegmentProcessorStage2Onwards(context, Constants.STG_2_FILE, 2).doProcess();
        new SegmentProcessorStage2Onwards(context, Constants.STG_3_FILE, 3).doProcess();
        new SegmentProcessorStage2Onwards(context, Constants.STG_4_FILE, 4).doProcess();
        new UpdateRootMetric(context).doProcess();
        new GenerateJson(context, Constants.JSON_OUTPUT).doProcess();
        long milliSec2 = new Date().getTime();
        System.err.println("Completed Json Generation in :" + (milliSec2 - milliSec1) + "ms ..Check Result in Data Folder");
    }
}
