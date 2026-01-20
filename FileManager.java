//Supposed to be used for leaderboard purposes: Currently not used, if time was there I really wanted to do a leaderboard but as I was working individually and did not start with a lot of AI code...I just didn't have time :(
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public static void saveProfile(StudyProfile profile, double efficiency) {
        try (FileWriter writer = new FileWriter("study_data.txt", true)) {
            writer.write(profile.getMark() + "," +
                         profile.getHours() + "," +
                         efficiency + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
