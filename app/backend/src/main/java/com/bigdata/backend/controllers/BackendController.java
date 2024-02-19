package com.bigdata.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.services.ImportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class BackendController {

    private final ImportService importService;

    @Autowired
    public BackendController(ImportService importService) {
        this.importService = importService;
    }

    @Operation(summary = "Import data from CSV file", description = "This endpoint allows you to load data from a csv file to the neo4j database. You need to send a 'multipart/form-data' data consisting of file: binary csv file and config: a configuration array in JSON string format based on ImportConfig Schema. Each element in the config array denote a specific node in the graph database. Note: If you specified a relationship on a node, don't specify the relationship on the other node configuration. Please refer to '/config-editor' endpoint documentation to see how a valid json configuration looks like.")
    @RequestMapping(value = "/csv", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String loadCsv(@Parameter(description = "CSV file") @RequestPart("file") MultipartFile file,
            @Parameter(description = "A JSON array based on the ImportConfig Schema. Note: Check the ImportConfig schema section.") @RequestPart("config") String config) {
        this.importService.importCsv(file);
        return "CSV file uploaded successfully";
    }

    @Operation(summary = "To check if the json config valid or not.", description = "You can use this to define your configuration here for '/csv' endpoint according to your data model. If the response is true, you can use the config in the '/csv' endpoint.")
    @PostMapping("config-editor")
    public boolean validateImportConfiguration(@RequestBody List<ImportConfig> config) {
        return true;
    }

}
