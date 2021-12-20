package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * Provides methods for reading current directory and renaming the
 * files according to set format.
 *
 * @author choondrise
 */
public class ParserUtil {

    /**
     * Fixes incompatible wrong date input (H:mm -> HH:mm), e.g. 3:00 instead of 03:00.
     *
     * @param date date to be fixed
     * @return fixed date format (HH:mm)
     */
    public static String dateFormatFix(String date) {
        if (date.length() == 4) {
            return "0" + date;
        }
        return date;
    }

    /**
     * @return map of string, string consisting of current directory text files
     * and their names
     */
    public static Map<String, String> readDirContent(String path) throws IOException {
        String dirPath = System.getProperty("user.dir");
        File currentDir = new File(dirPath + path);

        if (!currentDir.isDirectory()) {
            throw new IOException("Invalid program position, must be in a directory with readable text files.");
        }

        Map<String, String> files = new TreeMap<>();

        for (File file : Objects.requireNonNull(currentDir.listFiles())) {
            if (file.isFile()) {
                InputStream is = Files.newInputStream(file.toPath());
                String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                files.put(file.getName(), text);
            }
        }
        return files;
    }

}
