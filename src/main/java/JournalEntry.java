import java.util.Date;

public class JournalEntry implements Comparable<JournalEntry> {

    private Date date;
    private double rating;
    private Date start;
    private Date end;
    private String events;
    private String thoughts;

    public JournalEntry(Date date, double rating, Date start, Date end, String events, String thoughts) {
        this.date = date;
        this.rating = rating;
        this.start = start;
        this.end = end;
        this.events = events;
        this.thoughts = thoughts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
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

}
