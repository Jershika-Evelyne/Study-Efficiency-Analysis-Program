public class RecommendationEngine {

    // Recommend strategies based on method and efficiency
    public static String recommendTechnique(double efficiency, double hours, String method) {

        //Using switch statement to give feedback accoriding to the case (study method)
        switch (method) {
            case "Rereading":
                if (efficiency < 60) return "Switch to Active Recall or Practice Questions for better retention.";
                else return "Try adding Spaced Practice to reinforce learning.";

            case "Passive Notes":
                if (efficiency < 60) return "Combine note-taking with Active Recall to improve efficiency.";
                else return "Review notes actively instead of passively reviewing.";

            case "Highlighting":
                if (efficiency < 60) return "Use Practice Questions or Spaced Practice rather than just highlighting.";
                else return "Combine highlighting with Active Recall to boost retention.";

            case "Active Recall":
                if (efficiency < 85) return "Increase study frequency or combine with Spaced Practice for maximum results.";
                else return "Excellent method! Maintain current strategy.";

            case "Spaced Practice":
                if (efficiency < 85) return "Add Practice Questions or Active Recall to maximize efficiency.";
                else return "Good job! Keep consistent sessions for long-term retention.";

            case "Practice Questions":
                if (efficiency < 85) return "Combine with Spaced Practice to see further improvement.";
                else return "Strong strategy! Continue with this method.";

            case "Group Study / Discussion":
                if (efficiency < 85) return "Add Active Recall or Practice Questions to strengthen understanding.";
                else return "Effective group sessions! Consider occasional solo review for efficiency.";

            default:
                return "Maintain current strategy with minor review sessions.";
        }
    }

    // Estimated improvement (general)
    public static int predictImprovement(double efficiency) {
        if (efficiency < 60) return 12; // big change
        if (efficiency < 85) return 6;  // moderate improvement
        return 3;                        // small optimization
    }
}