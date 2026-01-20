public abstract class StudyProfile {

    //Variable 
    protected String name;
    protected String subject;
    protected double hours;
    protected double mark;

    //Methods are overridden to be used by the 2 distinct study profiles
    public StudyProfile(String name, String subject, double hours, double mark) {
        this.name = name;
        this.subject = subject;
        this.hours = hours;
        this.mark = mark;
    }

    public double getHours() {
        return hours;
    }

    public double getMark() {
        return mark;
    }

    public abstract double calculateEfficiency();
}