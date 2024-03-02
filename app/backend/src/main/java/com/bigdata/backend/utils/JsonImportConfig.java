package com.bigdata.backend.utils;

import com.bigdata.backend.exceptions.ImportCsvFileException;
import com.bigdata.backend.models.Column;
import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.models.Relationship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonImportConfig {
    private String jsonFormat;

    public JsonImportConfig(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    public String getJsonFormat() {
        return jsonFormat;
    }

    public void setJsonFormat(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    public List<ImportConfig> getImportConfigObject() {
        List<ImportConfig> importConfigs = new ArrayList<ImportConfig>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> config = objectMapper.readValue(this.jsonFormat, new TypeReference<List<Map<String, Object>>>() {
            });

            config.forEach(configItem -> {
                ImportConfig importConfig = new ImportConfig();
                importConfig.setName(configItem.get("name").toString());

                try {
                    String columnJsonString = objectMapper.writeValueAsString(configItem.get("key"));
                    Column columnConfig = objectMapper.readValue(columnJsonString, new TypeReference<Column>() {
                    });
                    if(columnConfig!=null){
                        importConfig.setKey(columnConfig);
                    }

                    String columnsJsonString = objectMapper.writeValueAsString(configItem.get("columns"));
                    Column[] columnsConfig = objectMapper.readValue(columnsJsonString, new TypeReference<Column[]>() {
                    });
                    if(columnsConfig!=null){
                        importConfig.setColumns(columnsConfig);
                    }


                    String relationshipsJsonString = objectMapper.writeValueAsString(configItem.get("relationships"));
                    Relationship[] relationshipsConfig = objectMapper.readValue(relationshipsJsonString, new TypeReference<Relationship[]>() {
                    });
                    if(relationshipsConfig!=null){
                        importConfig.setRelationships(relationshipsConfig);
                    }
                    importConfigs.add(importConfig);

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }

            });


        } catch (Exception exception) {
            throw new ImportCsvFileException(exception.getMessage());
        }

        return importConfigs;
    }
}
