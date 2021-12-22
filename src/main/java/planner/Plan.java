package planner;

/**
 * Represents a single plan.
 *
 * @author choondrise
 */
public class Plan {

    /**
     * Represents plan completion status, <code>true</code> if completed,
     * <code>false</code> otherwise.
     */
    private boolean completed;

    /**
     * Describes what plan actually is.
     */
    private String planDescription;

    /**
     * Class constructor that initializes all the parameters.
     *
     * @param planDescription description to be set
     */
    public Plan(boolean completed, String planDescription) {
        this.completed = completed;
        this.planDescription = planDescription;
    }

    // TODO: add javadoc

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (completed)
            sb.append("[+]\t");
        else
            sb.append("[-]\t");

        sb.append(planDescription).append("\n");

        return sb.toString();
    }
}
