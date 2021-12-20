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
    private boolean status;

    /**
     * Describes what plan actually is.
     */
    private String planDescription;

    /**
     * Class constructor that initializes all the parameters.
     *
     * @param planDescription description to be set
     */
    public Plan(boolean status, String planDescription) {
        this.status = status;
        this.planDescription = planDescription;
    }

    // TODO: add javadoc

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

        if (status)
            sb.append("[+]\t");
        else
            sb.append("[-]\t");

        sb.append(planDescription).append("\n");

        return sb.toString();
    }
}
