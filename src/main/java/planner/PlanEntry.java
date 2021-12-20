package planner;

/**
 * Represents one plan entry, either weekly, monthly or yearly.
 *
 * @author choondrise
 */
public class PlanEntry {

    /**
     * Type of this plan.
     */
    private PlanType type;

    /**
     * Array of professional plans.
     */
    private Plan[] professionalPlans;

    /**
     * Array of personal plans.
     */
    private Plan[] personalPlans;

    public PlanEntry (PlanType type, Plan[] professionalPlans, Plan[] personalPlans) {
        this.type = type;
        this.professionalPlans = professionalPlans;
        this.personalPlans = personalPlans;
    }

    // TODO: add javadoc

    public PlanType getType() {
        return type;
    }

    public Plan[] getProfessionalPlans() {
        return professionalPlans;
    }

    public Plan[] getPersonalPlans() {
        return personalPlans;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public void setProfessionalPlans(Plan[] professionalPlans) {
        this.professionalPlans = professionalPlans;
    }

    public void setPersonalPlans(Plan[] personalPlans) {
        this.personalPlans = personalPlans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[PROFESSIONAL]\n");

        for (Plan p : professionalPlans) {
            sb.append("\t").append(p);
        }

        sb.append("[PERSONAL]\n");
        for (Plan p : personalPlans) {
            sb.append("\t").append(p);
        }

        sb.append("\n");

        return sb.toString();
    }
}
