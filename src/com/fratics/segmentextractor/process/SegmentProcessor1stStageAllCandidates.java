package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.json.Value;
import com.fratics.segmentextractor.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class SegmentProcessor1stStageAllCandidates extends ProcessableAllCandidates {

    private String stageFile;
    private int stage;

    public SegmentProcessor1stStageAllCandidates(Context context, String stageFile, int stage) {
        this.context = context;
        this.stageFile = stageFile;
        this.stage = stage;
    }

    public void doProcess() {
        String lineStore = "";
        try {
            double tmpValue = 0.0;
            double currValue = 0.0;
            String s;
            //System.err.println(context.candidateSet);
            BufferedReader reader = new BufferedReader(new FileReader(new File(Constants.DATA_DIR + "/" + stageFile)));
            while ((s = reader.readLine()) != null) {

                ArrayList<String> candidateSet = new ArrayList<>();
                Value v = context.rootNode;

                boolean candidateFound = true;
                for (String candidate : candidateSet) {
                    if (!s.contains(candidate)) {
                        candidateFound = false;
                        break;
                    }
                }
                if (!candidateFound) continue;
                //System.err.println("xxx ==> " + s);
                String[] str = s.split(Constants.METRIC_SEPERATOR);
                String[] str1 = str[1].split(Constants.FIELD_SEPERATOR);


                try {
                    currValue = Double.parseDouble(str1[1]);
                } catch (NumberFormatException e) {
                    currValue = 0;
                }

                addLineToJson(str[0], currValue, candidateSet, v);

            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pruneUpdateCandidates(new ArrayList<String>(), context.rootNode);
        //System.err.println(context.currValueStores);
    }
}
