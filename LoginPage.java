import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginPage extends JPanel {

    private JTextField userField = new JTextField(10);
    private JPasswordField passField = new JPasswordField(10);

    private JLabel status = new JLabel("Student Per Week Efficiency Calculator");
    private JLabel status2 = new JLabel("Welcome!");

    private AppFrame appFrame;
    private CardLayout card = new CardLayout();

    public static String currentUser;

    private Image loginBackground = new ImageIcon("c:\\cs\\login_bg.jpeg").getImage(); //Loading bg image
    ImageIcon instructionIcon = new ImageIcon("info.png"); //icon used as it's not background 
    JLabel instructionImage = new JLabel(instructionIcon);
    ImageIcon icon = new ImageIcon("info2.png");
    Image img = icon.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH); //size image to be smaller than original
    JLabel bottomRightImage = new JLabel(new ImageIcon(img));

    public LoginPage(AppFrame frame) {
        this.appFrame = frame;
        setLayout(card);
        setPreferredSize(new Dimension(400, 400));

        status.setForeground(Color.WHITE);
        status.setBackground(new Color(0, 0, 0, 150));
        status.setOpaque(true);

        //Login Panel 
        JPanel loginPage = new JPanel(new GridBagLayout());
        loginPage.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        loginPage.add(status, gbc);

        gbc.gridy++;
        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("Username:"));
        userPanel.add(userField);
        loginPage.add(userPanel, gbc);

        gbc.gridy++;
        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passField);
        loginPage.add(passPanel, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        buttonPanel.add(registerBtn);
        buttonPanel.add(loginBtn);
        loginPage.add(buttonPanel, gbc);

        /*  INSTRUCTION PANEL  */
        JPanel instructionPage = new JPanel(new GridBagLayout());
        instructionPage.setBackground(new Color(180, 210, 255));
        instructionPage.setOpaque(true);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.insets = new Insets(5, 5, 5, 5);
        igbc.gridx = 0;
        igbc.gridy = 0;

        //All Intructions
        instructionPage.add(status2, igbc);

        igbc.gridy++;
        instructionPage.add(new JLabel("HOW TO USE"), igbc);
        igbc.gridy++;
        instructionPage.add(new JLabel("Enter your study info honestly"), igbc);
        igbc.gridy++;
        instructionPage.add(new JLabel("Efficiency is calculated from hours vs results"), igbc);
        igbc.gridy++;
        instructionPage.add(new JLabel("Follow recommendations to improve"), igbc);
        
        //Option to LogOut or head to effieciency calculator 
        igbc.gridy++;
        JPanel instructionButtons = new JPanel();
        JButton startBtn = new JButton("Start Efficiency Calculator");
        JButton logoutBtn = new JButton("Log Out");
        instructionButtons.add(startBtn);
        instructionButtons.add(logoutBtn);
        instructionPage.add(instructionButtons, igbc);

        //1st image 
        igbc.gridx = 0;
        igbc.anchor = GridBagConstraints.CENTER;
        igbc.gridx = 1;
        igbc.gridy = 0;
        igbc.anchor = GridBagConstraints.NORTHEAST;
        instructionPage.add(instructionImage, igbc);

        //2nd Image
        igbc.gridx = 1;                 
        igbc.gridy++;
        igbc.weightx = 1;
        igbc.weighty = 1;

        igbc.anchor = GridBagConstraints.SOUTHWEST;
        instructionPage.add(bottomRightImage, igbc);



        //Add Cars
        add(loginPage, "login");
        add(instructionPage, "instruction");
        card.show(this, "login");

        //All Buttons      

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> handleRegister());

        startBtn.addActionListener(e -> {
            appFrame.showInput(); // go to InputPanel --> Next action 
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            card.show(this, "login");
            status.setText("Student Efficiency Calculator (Per Week)");
        });

        ensureUserFileExists();
    }

   
    //Validtion class for registering + login (from previous assignment/work in class in Unit 4)
    private void handleLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            status.setText("Fields cannot be empty.");
            return;
        }

        if (validateLogin(user, pass)) {
            status2.setText("Welcome " + user + "!");
            card.show(this, "instruction");
            userField.setText("");
            passField.setText("");
        }
    }

    //Validtion class for registering + login (from previous assignment/work in class in Unit 4)
    private void handleRegister() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            status.setText("Fields cannot be empty.");
            return;
        }

        if (usernameExists(user)) {
            status.setText("Username already taken.");
            return;
        }

        saveUser(user, pass);
        currentUser = user;
        status2.setText("Welcome " + user + "!");
        card.show(this, "instruction");
    }

    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 &&
                    parts[0].equals(username) &&
                    parts[1].equals(password)) {

                    currentUser = username;
                    return true;
                }
            }
        } catch (IOException e) {
            status.setText("Error reading user file.");
        }
        status.setText("Invalid username or password.");
        return false;
    }

    private boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userInfo.txt", true))) {
            writer.write(username + "," + password + ",0,999");
            writer.newLine();
        } catch (IOException e) {
            status.setText("Error saving user.");
        }
    }

    private void ensureUserFileExists() {
        try {
            new File("userInfo.txt").createNewFile();
        } catch (IOException ignored) {}
    }

    //Painting Background 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, getWidth(), getHeight(), this);
    }
}
