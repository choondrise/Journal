package journal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

/**
 * Creates file with name dd-MM-yyyy inside resource directory
 * of this project. Also writes [RATING]\n[START]\n[END]\n[EVENTS]\n[SPENT]
 * inside.
 *
 * @author choondrise
 */
public class JournalEntryGenerator {

    /**
     * Gets local day, month and year, creates appropriate file
     * and writes content into it.
     *
     * @param args no args needed
     */
    public static void main(String[] args) {
        String day = String.format("%02d", LocalDate.now().getDayOfMonth());
        String month = String.format("%02d", LocalDate.now().getMonthValue());
        String year = String.valueOf(LocalDate.now().getYear());

        String fileName = String.format("%s-%s-%s", day, month, year);

        try {
            File file = new File(fileName);

            if (file.exists()) {
                System.out.println("File " + fileName + " already exists.");
            } else if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
                initializeJournalEntry(fileName);
            }
        } catch (IOException e) {
            System.out.println("Could not create file: " + fileName);
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Writes basic journal entry to file with name <code>fileName</code>.
     *
     * @param fileName name of the file to be written to
     */
    private static void initializeJournalEntry(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("[RATING]\n\n[START]\n\n[END]\n\n[EVENTS]\n\n[SPENT]\n\n");
            writer.close();
            System.out.println("Successfully wrote to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + fileName);
        }
    }
}
