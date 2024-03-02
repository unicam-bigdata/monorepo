package com.bigdata.backend.controllers;


import java.util.List;
import java.util.Map;

import com.bigdata.backend.dto.*;
import com.bigdata.backend.services.QueryService;
import com.bigdata.backend.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.services.ImportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class BackendController {

    private final ImportService importService;
    private final FileManager fileManager;
    private final QueryService queryService;


    @Autowired
    public BackendController(ImportService importService, FileManager fileManager, QueryService queryService) {
        this.importService = importService;
        this.fileManager = fileManager;
        this.queryService = queryService;

    }

    @Operation(summary = "Import data from CSV file", description = "This endpoint allows you to load data from a csv file to the neo4j database. You need to send a 'multipart/form-data' data consisting of file: binary csv file and config: a configuration array in JSON string format based on ImportConfig Schema. Each element in the config array denote a specific node in the graph database. Note: If you specified a relationship on a node, don't specify the relationship on the other node configuration. Please refer to '/config-editor' endpoint documentation to see how a valid json configuration looks like.")
    @RequestMapping(value = "/csv", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String loadCsv(@Parameter(description = "CSV file") @RequestPart("file") MultipartFile file,
                          @Parameter(description = "A JSON array based on the ImportConfig Schema. Note: Check the ImportConfig schema section.") @RequestPart("config") String config) {
        this.importService.importCsv(file, config);

        return "CSV file uploaded successfully";
    }

    @Operation(summary = "To check if the json config valid or not.", description = "You can use this to define your configuration here for '/csv' endpoint according to your data model. If the response is true, you can use the config in the '/csv' endpoint.")
    @PostMapping("config-editor")
    public boolean validateImportConfiguration(@RequestBody List<ImportConfig> config) {
        return true;
    }

    @Operation(summary = "Returns the imported csv file.", description = "The purpose of this endpoint is to serve the csv file uploaded for import by the user to the Neo4J database. Currently, the file is served from the memory after the user sends the csv using the '/csv'.")
    @GetMapping("cached-csv-file")
    public ResponseEntity<FileSystemResource> serveCsvFile() {
        // Create a FileSystemResource representing the local file
        FileSystemResource fileResource = new FileSystemResource(this.fileManager.getRootFileDirectory() + "import.csv");

        // Check if the file exists
        if (!fileResource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Set the appropriate content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileResource.getFilename());
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }

    @Operation(summary = "To clear the database.", description = "Clears the database.")
    @PostMapping("clear-database")
    public boolean clearDatabase() {
        this.importService.clearDatabase();
        return true;
    }

    @Operation(summary = "To get all nodes of a certain label.", description = "Returns nodes of the specified label. It is possible to get filtered data by passing filter configuration. You can define pagination by configuring the limit and skip parameters.")
    @PostMapping("nodes")
    public List<Map<String,Object>> getNodes(@RequestBody NodesRequest nodesRequest) {
        return this.queryService.getNodes(nodesRequest);
    }

    @Operation(summary = "To get the total count of nodes of a certain label.", description = "Returns total count of nodes of the specified label. It is possible to get the count of filtered data by passing filter configuration. This endpoint can utilized for pagination for example to determine total number of pages.")
    @PostMapping("nodes-count")
    public NodesCountResponse getNodesCount(@RequestBody NodesCountRequest nodesCountRequest) {
        return this.queryService.getNodesCount(nodesCountRequest);
    }

    @Operation(summary = "To get all nodes that are directly related to a specified node.", description = "Returns an array of nodes including their relationship that are directly related to the specified node. It is also possible to request the nodes that have a specific relationship to a node by passing the relationship filter configuration.")
    @PostMapping("related-nodes")
    public List<RelatedNodesResponse> getRelatedNodes(@RequestBody RelatedNodesRequest relatedNodesRequest) {
        return this.queryService.getRelatedNodes(relatedNodesRequest);
    }

}
