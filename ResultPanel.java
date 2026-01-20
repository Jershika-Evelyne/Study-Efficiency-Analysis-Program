import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {

    //Variables 
    private AppFrame frame;

    JLabel efficiencyLabel;
    JLabel categoryLabel;
    JLabel recommendationLabel;
    JLabel improvementLabel;
    JProgressBar progressBar;
    JButton backButton;
    int[] leftY;
    int[] rightY;
    String emoji;
    Timer timer;
    String pendSymbol;
    ImageIcon resultIcon = new ImageIcon("result.png");
    JLabel resultImage = new JLabel(resultIcon);

    public ResultPanel(AppFrame frame) {
        this.frame = frame;

        //Setting Border + Background 
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setBackground(new Color(180, 210, 255));
        setOpaque(true);

       

       // panel.setOpaque(true);

        JLabel title = new JLabel("Study Efficiency Result");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        efficiencyLabel = new JLabel();
        efficiencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryLabel = new JLabel();
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setMaximumSize(new Dimension(250, 25));

        recommendationLabel = new JLabel();
        recommendationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        improvementLabel = new JLabel();
        improvementLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> frame.showInput());

        add(Box.createVerticalGlue());   // pushes everything below down

        //Adding all components of Efficeincy Analysis (results)
        add(title);
        add(Box.createVerticalStrut(20));
        add(efficiencyLabel);
        add(Box.createVerticalStrut(10));
        add(progressBar);
        add(Box.createVerticalStrut(10));
        add(categoryLabel);
        add(Box.createVerticalStrut(15));
        add(recommendationLabel);
        add(Box.createVerticalStrut(5));
        add(improvementLabel);
        add(Box.createVerticalStrut(20));
        add(backButton);

        resultImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(resultImage); //Image added 
    }

    // Updated to also show the method used
    public void displayEfficiency(double efficiency, double hours, String method) {
        efficiencyLabel.setText("Efficiency Score: " + String.format("%.2f", efficiency));
        categoryLabel.setText("Category: " + EfficiencyCalculator.classify(efficiency));

        progressBar.setValue((int) efficiency);

        String recommendation = RecommendationEngine.recommendTechnique(efficiency, hours, method);
        int improvement = RecommendationEngine.predictImprovement(efficiency);

        recommendationLabel.setText("Recommended Strategy: " + recommendation);
        improvementLabel.setText("Estimated Improvement: +" + improvement + "%");
        
        //Emoji rain dependant on eddiciency score!
        if (efficiency >= 85) {
            categoryLabel.setText("Efficiency Level: High");
            pendSymbol = "👍";
        } else {
            categoryLabel.setText("Efficiency Level: Low");
            pendSymbol = "👎";
        }
    }

    //Class dictating animation flow!
    private void startAnimation(String chosenEmoji) {

    emoji = chosenEmoji;

    leftY = new int[6];
    rightY = new int[6];

    for (int i = 0; i < 6; i++) {
        leftY[i] = -i * 60;
        rightY[i] = -i * 60;
    }

    if (timer != null) {
        timer.stop();
    }

    timer = new Timer(30, e -> {
        for (int i = 0; i < leftY.length; i++) {
            leftY[i] += 3;
            rightY[i] += 3;
        }
        repaint();
    });

    timer.start();
    }

    public void startPendAnimation() {
        if (pendSymbol != null) {
            startAnimation(pendSymbol);
        }
    }

    //For emojis to shopw up (mainly)!
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
         // Choose color based on symbol
         
         g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

        if (leftY == null || rightY == null) return;

        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        for (int i = 0; i < leftY.length; i++) {
            g.drawString(emoji, 20, leftY[i]);
            g.drawString(emoji, getWidth() - 50, rightY[i]);
        }
    }


}