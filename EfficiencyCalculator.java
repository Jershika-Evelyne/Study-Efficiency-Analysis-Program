//Provided from AI...Deems an user as efficient or not

public class EfficiencyCalculator {

    public static String classify(double efficiency) {
        if (efficiency < 60) return "Inefficient";
        if (efficiency < 85) return "Average";
        return "Efficient";
    }

    //Not used...Done using reccomendation engine now!!
    public static String feedback(double efficiency) {
        if (efficiency < 60)
            return "Your study time is not converting well into results. Consider changing strategies.";
        if (efficiency < 85)
            return "Your study habits are decent but could be optimized.";
        return "Your study habits are very effective. Keep it up!";
    }
}