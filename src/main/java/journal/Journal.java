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
    double averageRating();

    /**
     *
     * @return
     */
    double averageSpent();

    /**
     *
     * @return
     */
    double averageSleep();

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
    double[] allRatings();

    /**
     *
     * @return
     */
    int[] allSpent();

    /**
     *
     * @return
     */
    double[] allSleeps();

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
