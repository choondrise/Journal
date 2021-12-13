package journal.impl;

import journal.Journal;
import journal.JournalEntry;
import journal.parser.EntryParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

/**
 *
 * @author choondrise
 */
public class JournalImpl implements Journal {

    private List<JournalEntry> entries;

    /**
     *
     * @param entries
     */
    public JournalImpl(List<JournalEntry> entries) {
        this.entries = entries;
    }

    @Override
    public float averageRating() {
        OptionalDouble avg = entries.stream().mapToDouble(JournalEntry::getRating).average();
        return avg.isPresent() ? (float) avg.getAsDouble() : 0.0f;
    }

    @Override
    public float averageSpent() {
        OptionalDouble avg = entries.stream().mapToDouble(JournalEntry::getSpent).average();
        return avg.isPresent() ? (float) avg.getAsDouble() : 0.0f;
    }

    @Override
    public float averageSleep() {
        float totalHrs = 0.0f;

        for (int i = 0; i < entries.size() - 1; i++) {
            Date start = entries.get(i + 1).getStart();
            Date end = entries.get(i).getEnd();

            long millisecondsSleep = start.getTime() - end.getTime();
            float hrs = millisecondsSleep * 1.0f / 3_600_000;
            totalHrs += hrs < 24 ? hrs : hrs - 24;
        }
        return totalHrs / entries.size();
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
    public float[] allRatings() {
        return new float[0];
    }

    @Override
    public float[] allSpent() {
        return new float[0];
    }

    @Override
    public float[] allSleeps() {
        return new float[0];
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
            // entries.forEach(System.out::println);
            Journal journal = new JournalImpl(entries);

            System.out.println("Average rating: " + journal.averageRating());
            System.out.println("Average sleep: " + journal.averageSleep());
            System.out.println("Average spent: " + journal.averageSpent());

        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getLocalizedMessage());
        }
    }
}
