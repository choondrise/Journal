package journal;

import java.util.Date;
import java.util.Map;

/**
 * Represents single day in life.
 *
 * @author choondrise
 */
public class JournalEntry implements Comparable<JournalEntry> {

    /**
     * Date of <code>JournalEntry</code>
     */
    private Date date;

    /**
     * subjective rating of the day
     */
    private double rating;

    /**
     * wakeup time in format hh:mm or h:mm
     */
    private Date start;

    /**
     * time went to sleep in format hh:mm or h:mm
     */
    private Date end;

    /**
     * description of main events happened that day
     */
    private String events;

    /**
     * amount of money (in HRK) spent that day
     */
    private int spent;

    /**
     * type of event happened that day
     */
    private EventType type;

    /**
     * Class constructor which initializes all the variables.
     *
     * @param date day date to be initialized
     * @param rating day rating to be initialized
     * @param start day start to be initialized
     * @param end day end to be initialized
     * @param events day events to be initialized
     * @param spent money spent that day to be initialized
     */
    public JournalEntry(Date date, double rating, Date start, Date end, String events, int spent, EventType type) {
        this.date = date;
        this.rating = rating;
        this.start = start;
        this.end = end;
        this.events = events;
        this.spent = spent;
        this.type = type;
    }

    /**
     * @return this day's date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date day <code>Date</code> to be set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return day rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating rating of the day to be set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return time woke up that day
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start time woke up to be set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return time went to sleep
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end time went to sleep to be set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return String describing events happened that day
     */
    public String getEvents() {
        return events;
    }

    /**
     * @param events description of events that day to be set
     */
    public void setEvents(String events) {
        this.events = events;
    }

    /**
     * @return amount of money spent
     */
    public int getSpent() {
        return spent;
    }

    /**
     * @param spent amount of money to be set
     */
    public void setSpent(int spent) {
        this.spent = spent;
    }

    /**
     * @return event type of that day
     */
    public EventType getType() {
        return type;
    }

    /**
     * @param type type of daily events to be set
     */
    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public int compareTo(JournalEntry o) {
        if (o == null) {
            throw new NullPointerException();
        }

        return (int) Math.signum(this.getRating() - o.getRating());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        if (!(o instanceof JournalEntry)) {
            throw new ClassCastException();
        }

        return this.getDate().equals(((JournalEntry) o).getDate());
    }

    @Override
    public String toString() {
        return String.format("%.2f", this.rating) + "\n" +
                this.events + "\n" +
                this.spent + "\n" +
                this.type;
    }

}
