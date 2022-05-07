package journal.parser;

import journal.EventType;
import journal.JournalEntry;
import org.w3c.dom.CDATASection;
import util.ParserUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Reads resource directory contents and returns <code>List</code>
 * of <code>JournalEntries</code> parsed from traversed documents.
 *
 * @author choondrise
 */
public class JournalEntryParser {

    private static final Comparator<? super String> DATE_COMPARATOR = (Comparator<String>) (date1, date2) -> {
        String[] components1 = date1.split("-");
        int day1 = Integer.parseInt(components1[0]);
        int month1 = Integer.parseInt(components1[1]);
        int year1 = Integer.parseInt(components1[2]);

        String[] components2 = date2.split("-");
        int day2 = Integer.parseInt(components2[0]);
        int month2 = Integer.parseInt(components2[1]);
        int year2 = Integer.parseInt(components2[2]);

        if (year1 == year2) {
            if (month1 == month2) {
                if (day1 == day2) {
                    return 0;
                }
                return Integer.compare(day1, day2);
            }
            return Integer.compare(month1, month2);
        }
        return Integer.compare(year1, year2);
    };

    /**
     * Home directory for all journal entry files.
     */
    private static final String PATH = "/src/main/resources/";

    /**
     * Parses current directory text files into list of <code>JournalEntries</code>
     * and returns it.
     *
     * @return list of journal entries parsed from current directory text files
     */
    public static List<JournalEntry> parse() throws IOException, ParseException {
        String dirPath = System.getProperty("user.dir");
        Map<String, String> files = new TreeMap<>(DATE_COMPARATOR);

        File homeDirectory = new File(dirPath + PATH);
        for (File file: Objects.requireNonNull(homeDirectory.listFiles())) {
            if (file.isDirectory() && file.getName().contains("-")) {
                Map<String, String> currentDirFiles = ParserUtil.readDirContent(PATH + file.getName());
                files.putAll(currentDirFiles);
            }
        }

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
            Date start = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + entryParts[3]);
            Date end = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + entryParts[5]);
            String events = entryParts[7];
            int spent = Integer.parseInt(entryParts[9]);
            EventType type = EventType.parse(entryParts[11]);

            entries.add(new JournalEntry(day, rating, start, end, events, spent, type));

        }

        return entries;
    }

}
