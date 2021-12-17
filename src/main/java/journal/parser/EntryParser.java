package journal.parser;

import journal.EventType;
import journal.JournalEntry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Reads resource directory contents and returns <code>List</code>
 * of <code>JournalEntries</code> parsed from traversed documents.
 *
 * @author choondrise
 */
public class EntryParser {

    /**
     * Fixes incompatible wrong date input (H:mm -> HH:mm), e.g. 3:00 instead of 03:00.
     *
     * @param date date to be fixed
     * @return fixed date format (HH:mm)
     */
    private static String dateFormatFix(String date) {
        if (date.length() == 4) {
            return "0" + date;
        }
        return date;
    }

    /**
     * @return map of string, string consisting of current directory text files
     * and their names
     */
    private static Map<String, String> readDirContent() throws IOException {
        String dirPath = System.getProperty("user.dir");
        File currentDir = new File(dirPath + "/src/main/resources/");

        if (!currentDir.isDirectory()) {
            throw new IOException("Invalid program position, must be in a directory with readable text files.");
        }

        Map<String, String> files = new TreeMap<>();

        for (File file : currentDir.listFiles()) {
            if (file.getName().matches("[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]")) {
                InputStream is = Files.newInputStream(file.toPath());
                String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                files.put(file.getName(), text);
            }
        }
        return files;
    }

    /**
     * Parses current directory text files into list of <code>JournalEntries</code>
     * and returns it.
     *
     * @return list of journal entries parsed from current directory text files
     */
    public static List<JournalEntry> parse() throws IOException, ParseException {
        Map<String, String> files = readDirContent();
        List<JournalEntry> entries = new ArrayList<>();

        for (Map.Entry<String, String> file : files.entrySet()) {
            String[] entryParts = file.getValue().split("\\n");

            if (entryParts.length < 12) continue;

            boolean cont = false;
            for (String entry : entryParts) {
                if (entry.equals("")) {
                    cont = true;
                    break;
                }
            }

            if (cont) continue;

            Date day = new SimpleDateFormat("dd-MM-yyyy").parse(file.getKey());
            float rating = Float.parseFloat(entryParts[1]);
            Date start = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + dateFormatFix(entryParts[3]));
            Date end = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + dateFormatFix(entryParts[5]));
            String events = entryParts[7];
            int spent = Integer.parseInt(entryParts[9]);
            String typeName = entryParts[11];

            EventType type;

            switch(typeName) {
                case "Z" -> type = EventType.Z;
                case "A" -> type = EventType.A;
                case "N" -> type = EventType.N;
                default -> throw new IOException("Cannot read event type");
            }

            entries.add(new JournalEntry(day, rating, start, end, events, spent, type));

        }

        return entries;
    }

}
