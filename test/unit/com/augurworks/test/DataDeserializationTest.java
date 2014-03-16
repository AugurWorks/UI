package com.augurworks.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.augurworks.web.data.DataTransferObjects;

public class DataDeserializationTest {

    private String readFile() throws IOException {
        return FileUtils.readFileToString(new File("test/resources/sample_data.json"));
    }

    @Test
    public void testDeserialize() throws Exception {
        DataTransferObjects.fromJsonString(readFile());
    }
}
