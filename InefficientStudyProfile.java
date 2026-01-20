public class InefficientStudyProfile extends StudyProfile {

    //Math differs from Efficient Study Profile 
    public InefficientStudyProfile(String name, String subject, double hours, double mark) {
        super(name, subject, hours, mark);
    }

    @Override
    public double calculateEfficiency() {
        double idealHours = 10; //2hrs per day * 5 (Study I found)
        double base = (mark / 100.0) / (hours / idealHours);

        double extraHours = Math.max(0, hours - idealHours);
        double penalty = 1 - (0.05 * extraHours);
        penalty = Math.max(0.5, penalty); //i.e. non-existent in efficient profile

        return Math.max(0, base * penalty * 100);
    }
}