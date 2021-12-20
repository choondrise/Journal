package main;

import journal.Journal;
import journal.JournalEntry;
import journal.parser.JournalEntryParser;
import planner.PlanEntry;
import planner.Planner;
import planner.parser.PlanEntryParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of <code>Journal</code> and <code>Planner</code> interface.
 * Provides inside into journaling and planning statistics.
 *
 * @author choondrise
 */
public class Main implements Journal, Planner {

    /**
     * <code>JournalEntries</code> used for calculating journaling statistics.
     * Generated using <code>JournalEntryParser</code>.
     */
    private List<JournalEntry> entries;

    /**
     * <code>PlanEntries</code> used for calculating planning statistics.
     * Generated using <code>PlanEntryParser</code>.
     */
    private List<PlanEntry> plans;

    /**
     * Class constructor which initializes list of
     * <code>JournalEntries</code>.
     *
     * @param entries list of journal entries to be initialized
     */
    public Main(List<JournalEntry> entries, List<PlanEntry> plans) {
        this.entries = entries;
        this.plans = plans;
    }

    @Override
    public double averageRating() {
        OptionalDouble avg = entries.stream().mapToDouble(JournalEntry::getRating).average();
        return avg.isPresent() ? avg.getAsDouble() : 0.0f;
    }

    @Override
    public double averageSpent() {
        OptionalDouble avg = entries.stream().mapToDouble(JournalEntry::getSpent).average();
        return avg.isPresent() ? avg.getAsDouble() : 0.0f;
    }

    @Override
    public double averageSleep() {
        double[] allSleeps = allSleeps();
        OptionalDouble avg = Arrays.stream(allSleeps).average();
        return avg.isPresent() ? avg.getAsDouble() : 0.0f;
    }

    @Override
    public Date bedTime() {
        return null;
    }

    @Override
    public Date wakeUpTime() {
        return null;
    }

    @Override
    public int topDayOfTheWeek() {
        return 0;
    }

    @Override
    public JournalEntry maxRatingEntry() {
        return null;
    }

    @Override
    public double[] allRatings() {
        return entries.stream().mapToDouble(JournalEntry::getRating).toArray();
    }

    @Override
    public int[] allSpent() {
        return entries.stream().mapToInt(JournalEntry::getSpent).toArray();
    }

    @Override
    public double[] allSleeps() {

        double[] sleeps = new double[entries.size() - 1];

        for (int i = 0; i < entries.size() - 1; i++) {
            Date start = entries.get(i + 1).getStart();
            Date end = entries.get(i).getEnd();

            long millisecondsSleep = start.getTime() - end.getTime();
            double hrs = millisecondsSleep * 1.0f / 3_600_000;
            if (hrs > 24) hrs -= 24;

            sleeps[i] = (hrs);
        }

        return sleeps;
    }

    @Override
    public boolean moneySpentDependsOnDayNumber() {
        return false;
    }

    @Override
    public boolean ratingDependsOnMoneySpent() {
        return false;
    }

    @Override
    public Map<String, Double> ratingBasedOnEventType() {
        Map<String, Double> ratings = entries.stream()
                        .collect(Collectors.groupingBy(e -> e.getType().toString(),
                                HashMap::new,
                                Collectors.averagingDouble(JournalEntry::getRating)));

        Map<String, Double> averages = new TreeMap<>();
        for (Map.Entry<String, Double> entry : ratings.entrySet()) {
            String key = entry.getKey();
            String newKey = "";
            Double rating = ratings.get(key);

            switch (key) {
                case "Z" -> newKey = "Zvrnzla";
                case "A" -> newKey = "Alkohol";
                case "N" -> newKey = "Nista";
            }

            averages.put(newKey, rating);
        }

        return averages;
    }

    @Override
    public Map<String, Double> ratingBasedOnWeekday() {
        Map<Integer, Double> ratings = entries.stream()
                .collect(Collectors.groupingBy(e -> {
                            LocalDate ld = LocalDate.ofInstant(e.getDate().toInstant(), ZoneId.systemDefault());
                            return ld.getDayOfWeek().getValue();
                        },
                        HashMap::new,
                        Collectors.averagingDouble(JournalEntry::getRating)));

        Map<String, Double> averages = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : ratings.entrySet()) {
            int key = entry.getKey();
            String newKey = "";
            Double rating = ratings.get(key);

            switch (key) {
                case 1 -> newKey = "MON";
                case 2 -> newKey = "TUE";
                case 3 -> newKey = "WED";
                case 4 -> newKey = "THU";
                case 5 -> newKey = "FRI";
                case 6 -> newKey = "SAT";
                case 7 -> newKey = "SUN";
            }

            averages.put(newKey, rating);
        }

        return averages;
    }

    @Override
    public int totalSpent() {
        return Arrays.stream(allSpent()).sum();
    }

    /**
     * Main method for running the program.
     */
    public static void main(String[] args) {
        List<JournalEntry> entries;
        List<PlanEntry> plans;

        try {
            entries = JournalEntryParser.parse();
            plans = PlanEntryParser.parse();
            Journal journal = new Main(entries, plans);

            // TODO: try communicating with user and output accordingly

            System.out.println("\n#############");
            System.out.println("## JOURNAL ##");
            System.out.println("#############\n\n\n");

            System.out.printf("Average rating\t: %5.2f\n", journal.averageRating());
            System.out.printf("Average sleep\t: %5.2f\n", journal.averageSleep());
            System.out.printf("Average spent\t: %5.2f\n", journal.averageSpent());

            System.out.println("-----------------------");
            journal.ratingBasedOnEventType().forEach((k, v) -> System.out.printf("%s\t -> %.2f\n", k, v));
            System.out.println("-----------------------");
            journal.ratingBasedOnWeekday().forEach((k, v) -> System.out.printf("%s\t -> %.2f\n", k, v));
            System.out.println("-----------------------");
            System.out.println("Spent HRK " + journal.totalSpent() + " in " + journal.allSpent().length + " days.");

            System.out.println("\n\n\n#############");
            System.out.println("### PLANS ###");
            System.out.println("#############\n\n\n");

            plans.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getLocalizedMessage());
        }
    }

    @Override
    public short totalPlansThisWeek() {
        return 0;
    }

    @Override
    public short completedPlansThisWeek() {
        return 0;
    }

    @Override
    public short totalPlansThisMonth() {
        return 0;
    }

    @Override
    public short completedPlansThisMonth() {
        return 0;
    }

    @Override
    public short totalPlansThisYear() {
        return 0;
    }

    @Override
    public short completedPlansThisYear() {
        return 0;
    }

    @Override
    public String[] allWeeklyPlans() {
        return new String[0];
    }

    @Override
    public String[] allMonthlyPlans() {
        return new String[0];
    }

    @Override
    public String[] allYearlyPlans() {
        return new String[0];
    }
}
