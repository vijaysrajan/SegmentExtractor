package com.fratics.segmentextractor.main;

import com.fratics.segmentextractor.json.GenerateJson;
import com.fratics.segmentextractor.process.*;
import com.fratics.segmentextractor.util.Constants;

import java.util.Date;

public class MainAllCandidates {
    public static void main(String[] args) {
        long milliSec1 = new Date().getTime();
        Context context = new Context();
        new SegmentProcessor1stStageAllCandidates(context, Constants.STG_1_FILE, 1).doProcess();
        new SegmentProcessorStage2OnwardsAllCandidates(context, Constants.STG_2_FILE, 2).doProcess();
        new SegmentProcessorStage2OnwardsAllCandidates(context, Constants.STG_3_FILE, 3).doProcess();
        new SegmentProcessorStage2OnwardsAllCandidates(context, Constants.STG_4_FILE, 4).doProcess();
        new UpdateRootMetric(context).doProcess();
        new GenerateJson(context, Constants.JSON_OUTPUT2).doProcess();
        long milliSec2 = new Date().getTime();
        System.err.println("Completed Json Generation in :" + (milliSec2 - milliSec1) + "ms ..Check Result in Data Folder");
    }
}
