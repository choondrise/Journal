package journal.impl;

import journal.Journal;
import journal.JournalEntry;
import journal.parser.EntryParser;

import java.util.*;

/**
 * Implementation of <code>Journal</code> interface.
 *
 * @author choondrise
 */
public class JournalImpl implements Journal {

    /**
     * <code>JournalEntries</code> used for calculating all the statistics.
     * Generated using <code>EntryParser</code>.
     */
    private List<JournalEntry> entries;

    /**
     * Class constructor which initializes list of
     * <code>JournalEntries</code>.
     *
     * @param entries list of journal entries to be initialized
     */
    public JournalImpl(List<JournalEntry> entries) {
        this.entries = entries;
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

    /**
     * Main method for running the program.
     */
    public static void main(String[] args) {
        List<JournalEntry> entries;

        try {
            entries = EntryParser.parse();
            Journal journal = new JournalImpl(entries);

            System.out.println("Average rating: " + journal.averageRating());
            System.out.println("Average sleep: " + journal.averageSleep());
            System.out.println("Average spent: " + journal.averageSpent());

            System.out.println("------------------");

            System.out.println("All ratings: " + Arrays.toString(journal.allRatings()));
            System.out.println("All sleeps: " + Arrays.toString(journal.allSleeps()));
            System.out.println("All spent: " + Arrays.toString(journal.allSpent()));

        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getLocalizedMessage());
        }
    }
}
