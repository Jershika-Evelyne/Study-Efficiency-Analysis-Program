public class EfficientStudyProfile extends StudyProfile {

    //Math differs from Inefficient Study Profile 
    public EfficientStudyProfile(String name, String subject, double hours, double mark) {
        super(name, subject, hours, mark);
    }

    @Override
    public double calculateEfficiency() {
        double idealHours = 10; //2hrs per day * 5 (Study I found)
        double base = (mark / 100.0) / (hours / idealHours);

        double bonus = 1 + 0.03 * Math.min(3, hours / 3);
        return Math.min(100, base * bonus * 100); //i.e. bonus is not present in other profile
    }
}