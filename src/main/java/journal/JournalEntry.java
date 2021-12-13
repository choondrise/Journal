package journal;

import java.util.Date;

/**
 *
 * @author choondrise
 */
public class JournalEntry implements Comparable<JournalEntry> {

    private Date date;
    private double rating;
    private Date start;
    private Date end;
    private String events;
    private int spent;

    /**
     *
     * @param date
     * @param rating
     * @param start
     * @param end
     * @param events
     * @param spent
     */
    public JournalEntry(Date date, double rating, Date start, Date end, String events, int spent) {
        this.date = date;
        this.rating = rating;
        this.start = start;
        this.end = end;
        this.events = events;
        this.spent = spent;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     */
    public Date getStart() {
        return start;
    }

    /**
     *
     * @param start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     *
     * @return
     */
    public Date getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     *
     * @return
     */
    public String getEvents() {
        return events;
    }

    /**
     *
     * @param events
     */
    public void setEvents(String events) {
        this.events = events;
    }

    /**
     *
     * @return
     */
    public int getSpent() {
        return spent;
    }

    /**
     *
     * @param spent
     */
    public void setSpent(int spent) {
        this.spent = spent;
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
                this.spent + "\n";
    }

}
