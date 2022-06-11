package com.cs.automationframework.utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileWriter {

    /**
     * Write a file to the file system, with the supplied file contents (String) and supplied filepath + fileextension (String)
     * Returns a boolean to indicate if file creation was successful
     * @param fileContents
     * @param filePath
     * @return
     */
    public static String write (String fileContents, String filePath){

        System.out.println("writing file with path " + filePath + " and contents:");
        System.out.println(fileContents);

        String finalPath = "";
        try {
            List<String> lines = Arrays.asList(fileContents);
            Path file = Paths.get(filePath);
            finalPath = file.toAbsolutePath().toString();

            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalPath;
    }

}
