import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame { //Central connector 

    //variable 
    private CardLayout layout = new CardLayout();
    private JPanel container = new JPanel(layout);
    private LoginPage loginPage;

    private InputPanel inputPanel;
    private ResultPanel resultPanel;

    public AppFrame() {
        setTitle("Study Efficiency Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        loginPage = new LoginPage(this);   
        inputPanel = new InputPanel(this);
        resultPanel = new ResultPanel(this);
        
        //Adding all screens
        container.add(loginPage, "LOGIN");
        container.add(inputPanel, "INPUT");
        container.add(resultPanel, "RESULT");

        add(container);
        layout.show(container, "LOGIN");   // show login first
        setVisible(true);
    }

    //As the name states, for result panel to call its methods + for animation
    public void showResult(double efficiency, double hours, String method) {
        resultPanel.displayEfficiency(efficiency, hours, method);
        layout.show(container, "RESULT");
        
        SwingUtilities.invokeLater(() -> {
            resultPanel.startPendAnimation();
        });
}

    public void showInput() {
        layout.show(container, "INPUT");
    }
}
