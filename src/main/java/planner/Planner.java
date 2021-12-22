package planner;

/**
 * Simulates weekly, monthly and yearly plans.
 *
 * @author choondrise
 */
public interface Planner {

    /**
     * @return number of total made plans based on <code>PlanType</code>
     */
    short totalPlansByInterval(PlanType type);

    /**
     * @return number of completed plans based on <code>PlanType</code>
     */
    short completedPlansByInterval(PlanType type);

    /**
     * Returns percentage of completed plans in an interval based on
     * given <code>PlanType</code>.
     *
     * @param type type whose percentage is to be calculated
     * @return calculated percentage
     */
    default String percentageOfCompletedPlansByInterval(PlanType type) {
        double pct = (completedPlansByInterval(type) * 1.0D) / totalPlansByInterval(type) * 100;
        return String.format("%.2f", pct) + "%";
    }

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
