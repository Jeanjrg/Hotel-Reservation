
package com.hotelapp.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDatabase {
    private String basePath;

    public JsonDatabase(String basePath) {
        this.basePath = basePath;
    }

    public String readJsonFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(basePath, filename)));
    }

    public void writeJsonFile(String filename, String jsonContent) throws IOException {
        Files.write(Paths.get(basePath, filename), jsonContent.getBytes());
    }
}
