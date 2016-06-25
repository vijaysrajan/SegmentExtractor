package com.fratics.segmentextractor.auto;

import com.fratics.segmentextractor.process.Context;
import com.fratics.segmentextractor.process.Processable;
import com.fratics.segmentextractor.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class AutoDetectDimension extends Processable {

    public AutoDetectDimension(Context context) {
        this.context = context;
    }

    public void doProcess() {
        String candidate = null;
        try {
            String s;
            double tmpValue = 0.0;
            double currValue = 0.0;
            BufferedReader reader = new BufferedReader(new FileReader(new File(Constants.DATA_DIR + "/" + Constants.STG_1_FILE)));
            while ((s = reader.readLine()) != null) {
                //System.err.println(s);
                String[] str = s.split(Constants.METRIC_SEPERATOR);
                String[] str1 = str[1].split(Constants.FIELD_SEPERATOR);
                try {
                    currValue = Double.parseDouble(str1[1]);
                } catch (NumberFormatException e) {
                }
                if (currValue > tmpValue) {
                    tmpValue = currValue;
                    candidate = str[0].split(Constants.FIELD_SEPERATOR)[0].split(Constants.STG_SEPERATOR)[1];
                } else {
                    currValue = 0.0;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.candidateSet.add(candidate);
    }

    public static void main(String[] args) {
        //dummy main for testing
        long milliSec1 = new Date().getTime();
        Context context = new Context();
        new AutoDetectDimension(context).doProcess();
        System.err.println(context.candidateSet);
        long milliSec2 = new Date().getTime();
        System.err.println("Time to execute :" + (milliSec2 - milliSec1) + "ms");
    }
}
