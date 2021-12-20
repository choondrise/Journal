package planner;

/**
 * Simulates weekly, monthly and yearly plans.
 *
 * @author choondrise
 */
public interface Planner {

    /**
     * @return number of total made plans this week
     */
    short totalPlansThisWeek();

    /**
     * @return number of completed plans this week
     */
    short completedPlansThisWeek();

    /**
     * @return number of total made plans this month
     */
    short totalPlansThisMonth();

    /**
     * @return number of completed plans this month
     */
    short completedPlansThisMonth();

    /**
     *
     * @return number of total made plans this year
     */
    short totalPlansThisYear();

    /**
     * @return number of completed plans this year
     */
    short completedPlansThisYear();

    /**
     * @return array of all weekly plans with completion status
     */
    String[] allWeeklyPlans();

    /**
     * @return array of all monthly plans with completion status
     */
    String[] allMonthlyPlans();

    /**
     * @return array of all yearly plans with completion status
     */
    String[] allYearlyPlans();

}
