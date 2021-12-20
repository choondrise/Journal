package planner.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Generates plan: creates a file in directory resources/plans and
 * writes [PROFESSIONAL]\n[PERSONAL] to it.
 *
 * @author choondrise
 */
public class PlanEntryGenerator {

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
        String type;

        if (args.length > 0 && (args[0].startsWith("week") ||
                args[0].startsWith("month") ||
                args[0].startsWith("year"))) {
            type = args[0];
        } else {
            throw new IllegalArgumentException("Argument must be either week, month or year.");
        }

        String fileName = generateFileName(type, day, month, year);
        String dirName = System.getProperty("user.dir");

        try {
            File file = new File(dirName + "/src/main/resources/plans/" + fileName);

            if (file.exists()) {
                System.out.println("File " + file.getName() + " already exists.");
            } else if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                initializePlanEntry(file);
            }
        } catch (IOException e) {
            System.out.println("Could not create file: " + fileName);
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Writes basic plan entry to file with name <code>fileName</code>.
     *
     * @param file file to be written to
     */
    private static void initializePlanEntry(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("[PROFESSIONAL]\n\n[PERSONAL]\n");
            writer.close();
            System.out.println("Successfully wrote to file: " + file);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + file);
        }
    }

    /**
     * Generates file name with given <code>day</code>, <code>month</code>
     * and <code>year</code> based on provided <code>type</code>. For example,
     * if given arguments are day: 7, month: 12 and year: 2021, generated file
     * name can be 'week-07-12-2021', or 'month-12-2021' or 'year-2021' based
     * on <code>type</code> parameter.
     *
     * @param type type of plan (week, month, year)
     * @param day day of the plan
     * @param month month of the plan
     * @param year year of the plan
     * @return generated plan file name
     */
    private static String generateFileName(String type, String day, String month, String year) {
        switch (type) {
            case "week" -> {
                return String.format("week-%s-%s-%s", day, month, year);
            }
            case "month" -> {
                return String.format("month-%s-%s", month, year);
            }
            case "year" -> {
                return String.format("year-%s", year);
            }
        }

        throw new IllegalArgumentException("Parameter type can only be 'week', 'month' or 'year'.");
    }
}
