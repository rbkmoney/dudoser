package com.rbkmoney.dudoser.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileHelper {

    public final static String FILENAME_LAST_EVENT_ID = "last_event_id.txt";

    public static String pathToFolder;

    public static String getLastEventId() throws IOException {
        return FileHelper.readFile(getFile(FILENAME_LAST_EVENT_ID).getAbsolutePath());
    }

    public static File getFile(String filename) throws IOException {
        File file = new File(FileHelper.pathToFolder + filename);
        if (!file.exists()) {
            if(!Optional.ofNullable(file.getParentFile()).isPresent()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName))).trim();
    }

    public static void writeFile(String fileName, String text) throws IOException {
        Files.write(Paths.get(fileName), text.trim().getBytes());
    }

}
