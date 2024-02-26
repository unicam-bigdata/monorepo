package com.bigdata.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVJSONMapper {
    private List<String[]> csv;
    private String json;

    public CSVJSONMapper() {

    }

    public List<String[]> getCsv() {
        return csv;
    }

    public String getJson() {
        return json;
    }

    public CSVJSONMapper(MultipartFile file) {
        try {
            this.readCsvFile(file);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    public void readCsvFile(MultipartFile file) throws Exception {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            this.csv = csvReader.readAll();
            this.json = this.generateJsonFromCsv(this.csv);
        }
    }

    private String generateJsonFromCsv(List<String[]> csv) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        String[] headers = csv.get(0);
        for (int i = 1; i < csv.size(); i++) {
            Map<String, Object> object = new HashMap<>();
            for (int j = 0; j < csv.get(i).length; j++) {
                object.put(headers[j], csv.get(i)[j]);
            }
            arrayList.add(object);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(arrayList);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return "{}";
    }

}
