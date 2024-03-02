package com.bigdata.backend.utils;

import com.bigdata.backend.exceptions.ImportCsvFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    private final String rootFileDirectory;

    public FileManager() {
        this.rootFileDirectory = "files/";
        setupDirectory();
    }

    public String getRootFileDirectory() {
        return rootFileDirectory;
    }

    public void setupDirectory() {
        File directory = new File(this.rootFileDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created.");
            } else {
                System.out.println("Directory creation failed.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }

    public void storeImportFile(MultipartFile file) {
        File destinationFile = new File(this.rootFileDirectory + "import.csv");
        if (destinationFile.exists()) {
            try {
                boolean fileDeleted = destinationFile.delete();
                if (fileDeleted) {
                    System.out.println("Existing import file deleted successfully.");
                } else {
                    System.out.println("Existing import file delete failed.");
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        Path filepath = Paths.get(this.rootFileDirectory, "import.csv");
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        } catch (Exception exception) {
            throw new ImportCsvFileException(exception.getMessage());
        }
    }
}
