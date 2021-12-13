package journal;

import java.util.Date;

/**
 *
 * @author choondrise
 */
public interface Journal {

    /**
     *
     * @return
     */
    float averageRating();

    /**
     *
     * @return
     */
    float averageSpent();

    /**
     *
     * @return
     */
    float averageSleep();

    /**
     *
     * @return
     */
    Date bedTime();

    /**
     *
     * @return
     */
    Date wakeUpTime();

    /**
     *
     * @return
     */
    int topDayOfTheWeek();

    /**
     *
     * @return
     */
    JournalEntry maxRatingEntry();

    /**
     *
     * @return
     */
    float[] allRatings();

    /**
     *
     * @return
     */
    float[] allSpent();

    /**
     *
     * @return
     */
    float[] allSleeps();

    /**
     *
     * @return
     */
    boolean moneySpentDependsOnDayNumber();

    /**
     *
     * @return
     */
    boolean ratingDependsOnMoneySpent();

}
