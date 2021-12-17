package journal;

import java.util.Date;
import java.util.Map;

/**
 * Represents Journal which keeps track of rating, day start,
 * day end, events and money spent in each day. Journal also
 * provides some basic statistics about chosen span of days.
 *
 * @author choondrise
 */
public interface Journal {

    /**
     * @return average rating of all days
     */
    double averageRating();

    /**
     * @return average money spent in all days
     */
    double averageSpent();

    /**
     * @return average sleep in all days
     */
    double averageSleep();

    /**
     * @return average time went to bed
     */
    Date bedTime();

    /**
     * @return average time woke up
     */
    Date wakeUpTime();

    /**
     * @return best rated day in week
     */
    int topDayOfTheWeek();

    /**
     * @return top-rated day ever
     */
    JournalEntry maxRatingEntry();

    /**
     * @return array of all the ratings
     */
    double[] allRatings();

    /**
     * @return array of all money spent
     */
    int[] allSpent();

    /**
     * @return array of all time slept
     */
    double[] allSleeps();

    /**
     * @return <code>true</code> if money spent depends on day number,
     * <code>false</code> otherwise
     */
    boolean moneySpentDependsOnDayNumber();

    /**
     * @return <code>true</code> if rating depends on day number,
     * <code>false</code> otherwise
     */
    boolean ratingDependsOnMoneySpent();

    /**
     * @return <code>Map</code> with keys representing event type and
     * entries representing average rating of days with that event type
     */
    Map<String, Double> ratingDependingOnEventType();

}
