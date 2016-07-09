package com.fratics.segmentextractor.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fratics.segmentextractor.process.Context;
import com.fratics.segmentextractor.process.Processable;
import com.fratics.segmentextractor.util.Constants;

import java.io.File;

public class GenerateJson extends Processable {

    public GenerateJson(Context context) {
        this.context = context;
    }

    public void doProcess() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(Constants.DATA_DIR + "/" + context.get("jsonfile")), context.rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
