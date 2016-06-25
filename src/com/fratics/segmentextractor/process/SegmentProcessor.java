package com.fratics.segmentextractor.process;

import com.fratics.segmentextractor.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SegmentProcessor extends Processable {

    private String stageFile;

    public SegmentProcessor(Context context, String stageFile) {
        this.context = context;
        this.stageFile = stageFile;
    }

    public void doProcess() {
        String lineStore = "";
        try {
            double tmpValue = 0.0;
            double currValue = 0.0;
            String s;
            //System.err.println(context.candidateSet);
            super.getCurrentValue();
            BufferedReader reader = new BufferedReader(new FileReader(new File(Constants.DATA_DIR + "/" + stageFile)));
            while ((s = reader.readLine()) != null) {
                boolean candidateFound = true;
                for (String candidate : context.candidateSet) {
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
                }

                addLineToJson(str[0], currValue);

                if (currValue > tmpValue) {
                    tmpValue = currValue;
                    lineStore = str[0];
                } else {
                    currValue = 0.0;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateCandidates(lineStore);
    }
}
