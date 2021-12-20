package planner.parser;

import planner.Plan;
import planner.PlanEntry;
import planner.PlanType;
import util.ParserUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reads resource/plans directory contents and returns <code>List</code>
 * of <code>PlanEntries</code> parsed from traversed documents.
 *
 * @author choondrise
 */
public class PlanEntryParser {

    /**
     * Parses current directory text files into list of <code>PlanEntries</code>
     * and returns it.
     *
     * @return list of plan entries parsed from current directory text files
     */
    public static List<PlanEntry> parse() throws IOException {
        Map<String, String> files = ParserUtil.readDirContent("/src/main/resources/plans/");
        List<PlanEntry> entries = new ArrayList<>();

        for (Map.Entry<String, String> file : files.entrySet()) {
            List<String> entryParts = List.of(file.getValue().split("\\n"));

            if (!entryParts.get(0).equals("[PROFESSIONAL]"))
                throw new IllegalArgumentException("Each plan entry must start with professional tab.");

            int index = entryParts.indexOf("[PERSONAL]");

            List<Plan> professionalPlans = new ArrayList<>();
            List<Plan> personalPlans = new ArrayList<>();

            for (int i = 1; i < index; i++) {
                String plan = entryParts.get(i);
                professionalPlans.add(new Plan(
                        isCompleted(plan),
                        parsePlan(plan)
                ));
            }

            for (int i = index + 1; i < entryParts.size(); i++) {
                String plan = entryParts.get(i);
                personalPlans.add(new Plan(
                        isCompleted(plan),
                        parsePlan(plan)
                ));
            }

            PlanType type;

            if (file.getKey().startsWith("week")) {
                type = PlanType.WEEKLY;
            } else if (file.getKey().startsWith("month")) {
                type = PlanType.MONTHLY;
            } else if (file.getKey().startsWith("year")) {
                type = PlanType.YEARLY;
            } else
                throw new IllegalArgumentException("File name must start with either 'week', 'month' or 'year'.");

            PlanEntry entry = new PlanEntry(
                    type,
                    professionalPlans.toArray(new Plan[0]),
                    personalPlans.toArray(new Plan[0])
            );

            entries.add(entry);
        }

        return entries;
    }

    /**
     * Returns <code>true</code> if plan is completed, false otherwise.
     *
     * @param plan plan to be evaluated if completed
     * @return completion status of the plan
     */
    private static boolean isCompleted(String plan) {
        return plan.startsWith("+");
    }

    /**
     * Gets plan description from whole plan format.
     *
     * @param plan plan whose description is to be returned
     * @return plan description
     */
    private static String parsePlan(String plan) {
        if (plan.startsWith("-") || plan.startsWith("+")) {
            return plan.substring(1);
        }
        throw new IllegalArgumentException("Plan must start with either '-' or '+'.");
    }
}
