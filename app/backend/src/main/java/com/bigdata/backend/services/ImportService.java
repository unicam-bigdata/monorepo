package com.bigdata.backend.services;

import java.util.List;

import com.bigdata.backend.exceptions.ImportCsvFileException;
import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.utils.FileManager;
import com.bigdata.backend.utils.JsonImportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bigdata.backend.repositories.ImportRepository;

@Service
public class ImportService {
    private final ImportRepository importRepository;
    private final FileManager fileManager;

    @Autowired
    public ImportService(ImportRepository importRepository, FileManager fileManager) {
        this.importRepository = importRepository;
        this.fileManager = fileManager;
    }

    public void importCsv(MultipartFile file, String jsonConfig) {

        try {
            fileManager.storeImportFile(file);
            JsonImportConfig jsonImportConfig = new JsonImportConfig(jsonConfig);
            List<ImportConfig> importConfigs = jsonImportConfig.getImportConfigObject();
            this.importRepository.loadData(importConfigs);

        } catch (Exception exception) {
            throw new ImportCsvFileException(exception.getMessage());
        }

    }

    public void clearDatabase() {

        this.importRepository.clearDatabase();
    }
}
