import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    //Variable 
    private AppFrame frame;

    JTextField hoursField;
    JTextField markField;
    JComboBox<String> methodBox;
    JButton calculateButton;

    public InputPanel(AppFrame frame) {
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        setBackground(new Color(180, 210, 255));
        setOpaque(true);

        JLabel title = new JLabel("Study Session Input");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        hoursField = new JTextField();
        markField = new JTextField();

        //Drop-Down for study method choice
        methodBox = new JComboBox<>(new String[]{
                "Rereading",
                "Passive Notes",
                "Highlighting",
                "Active Recall",
                "Spaced Practice",
                "Practice Questions",
                "Group Study / Discussion"
        });

        calculateButton = new JButton("Calculate Efficiency");
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Adding all components of the Input Screen
        add(title);
        add(Box.createVerticalStrut(20));
        add(createField("Hours Studied This Week", hoursField));
        add(createField("Mark This Week (%)", markField));
        add(createField("Study Method", methodBox));
        add(Box.createVerticalStrut(20));
        add(calculateButton);

        calculateButton.addActionListener(e -> calculate());
    }

    //Calculating efficiency (Weight Based)
    private void calculate() {
        try {
            double hours = Double.parseDouble(hoursField.getText());
            double mark = Double.parseDouble(markField.getText());

            //Forces the entries to be accurate (Should make sense)
            if (hours <= 0) throw new IllegalArgumentException("Hours must be > 0.");
            if (mark < 0 || mark > 100) throw new IllegalArgumentException("Mark must be 0-100.");

            String method = (String) methodBox.getSelectedItem();

            // Assign weights based on method
            double weight;
            StudyProfile profile;
            switch (method) {
                case "Active Recall":
                    weight = 1.2;
                    profile = new EfficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Spaced Practice":
                    weight = 1.0;
                    profile = new EfficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Practice Questions":
                    weight = 1.1;
                    profile = new EfficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Group Study / Discussion":
                    weight = 0.9;
                    profile = new EfficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Rereading":
                    weight = 0.7;
                    profile = new InefficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Passive Notes":
                    weight = 0.75;
                    profile = new InefficientStudyProfile("User", "Subject", hours, mark);
                    break;
                case "Highlighting":
                    weight = 0.8;
                    profile = new InefficientStudyProfile("User", "Subject", hours, mark);
                    break;
                default:
                    weight = 1.0;
                    profile = new EfficientStudyProfile("User", "Subject", hours, mark);
            }

            // Calculate efficiency
            double efficiency = profile.calculateEfficiency() * weight;

            // Save to file
            FileManager.saveProfile(profile, efficiency);

            // Show result (pass hours for recommendations)
            frame.showResult(efficiency, hours, method);

        //When something is caught (wrong) --> Panel shows entry is invalid!
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createField(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setMaximumSize(new Dimension(250, 25));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }
}
