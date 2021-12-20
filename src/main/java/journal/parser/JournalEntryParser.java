package journal.parser;

import journal.EventType;
import journal.JournalEntry;
import util.ParserUtil;

import java.io.IOException;
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

    /**
     * Parses current directory text files into list of <code>JournalEntries</code>
     * and returns it.
     *
     * @return list of journal entries parsed from current directory text files
     */
    public static List<JournalEntry> parse() throws IOException, ParseException {
        Map<String, String> files = ParserUtil.readDirContent("/src/main/resources/");
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
            Date start = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + ParserUtil.dateFormatFix(entryParts[3]));
            Date end = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(file.getKey() + " " + ParserUtil.dateFormatFix(entryParts[5]));
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
