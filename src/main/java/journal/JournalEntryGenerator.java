package journal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        String dirName = System.getProperty("user.dir");

        try {
            File file = new File(dirName + "/src/main/resources/" + fileName);

            if (file.exists()) {
                System.out.println("File " + file.getName() + " already exists.");
            } else if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                initializeJournalEntry(file);
            }
        } catch (IOException e) {
            System.out.println("Could not create file: " + fileName);
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Writes basic journal entry to file with name <code>fileName</code>.
     *
     * @param file file to be written to
     */
    private static void initializeJournalEntry(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("[RATING]\n\n[START]\n\n[END]\n\n[EVENTS]\n\n[SPENT]\n");
            writer.close();
            System.out.println("Successfully wrote to file: " + file);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + file);
        }
    }
}
