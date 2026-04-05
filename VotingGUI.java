import java.awt.*;
import javax.swing.*;

public class VotingGUI {

    static String[] users = new String[10];
    static String[] passwords = new String[10];
    static boolean[] voted = new boolean[10];

    static String[] candidates = {"BJP","BRS","CONGRESS"};
    static int[] votes = {0,0,0};

    static int userCount = 0;
    static int currentUser = -1;

    public static void main(String[] args) {

        UIManager.put("control", new Color(30,30,30));
        UIManager.put("text", new Color(230,230,230));

        mainMenu();
    }

    // 🏠 MAIN MENU
    static void mainMenu() {

        JFrame frame = new JFrame("Online Voting System");
        frame.setSize(420,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Online Voting System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(20,20,60));
        top.add(title);

        JPanel center = new JPanel(new GridLayout(3,1,15,15));
        center.setBorder(BorderFactory.createEmptyBorder(40,60,40,60));
        center.setBackground(new Color(40,40,80));

        JButton register = createButton("Register");
        JButton login = createButton("Login");
        JButton results = createButton("View Results");

        center.add(register);
        center.add(login);
        center.add(results);

        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        register.addActionListener(e -> registerForm());
        login.addActionListener(e -> loginForm());
        results.addActionListener(e -> showResults());
    }

    // 🔐 REGISTER
    static void registerForm() {

        JFrame frame = createFormFrame("Register");

        JTextField user = createTextField();
        JPasswordField pass = createPasswordField();

        JButton btn = createButton("Register");

        frame.add(createLabel("Username"));
        frame.add(user);
        frame.add(createLabel("Password"));
        frame.add(pass);
        frame.add(btn);

        frame.getRootPane().setDefaultButton(btn); // ENTER KEY

        frame.setVisible(true);

        btn.addActionListener(e -> {

            String u = user.getText().trim();
            String p = new String(pass.getPassword()).trim();

            // 🔥 EMPTY VALIDATION
            if(u.isEmpty() || p.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Username and Password cannot be empty!");
                return;
            }

            // 🔥 DUPLICATE CHECK
            for(int i=0;i<userCount;i++){
                if(u.equals(users[i])){
                    JOptionPane.showMessageDialog(frame,"Username already exists!");
                    return;
                }
            }

            if(userCount >= users.length){
                JOptionPane.showMessageDialog(frame,"User limit reached");
                return;
            }

            users[userCount] = u;
            passwords[userCount] = p;
            voted[userCount] = false;

            userCount++;

            JOptionPane.showMessageDialog(frame,"Registration Successful");
            frame.dispose();
        });
    }

    // 🔑 LOGIN
    static void loginForm() {

        JFrame frame = createFormFrame("Login");

        JTextField user = createTextField();
        JPasswordField pass = createPasswordField();

        JButton btn = createButton("Login");

        frame.add(createLabel("Username"));
        frame.add(user);
        frame.add(createLabel("Password"));
        frame.add(pass);
        frame.add(btn);

        frame.getRootPane().setDefaultButton(btn); // ENTER KEY

        frame.setVisible(true);

        btn.addActionListener(e -> {

            String u = user.getText().trim();
            String p = new String(pass.getPassword()).trim();

            // 🔥 EMPTY VALIDATION
            if(u.isEmpty() || p.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Enter Username and Password!");
                return;
            }

            for(int i=0;i<userCount;i++){

                if(u.equals(users[i]) && p.equals(passwords[i])){

                    if(voted[i]){
                        JOptionPane.showMessageDialog(frame,"Already voted!");
                        return;
                    }

                    currentUser = i;
                    frame.dispose();
                    votingScreen();
                    return;
                }
            }

            JOptionPane.showMessageDialog(frame,"Invalid Login");
        });
    }

    // 🗳️ VOTING SCREEN
    static void votingScreen() {

        JFrame frame = new JFrame("Vote");
        frame.setSize(400,450);
        frame.setLayout(new GridLayout(3,1,20,20));
        frame.getContentPane().setBackground(new Color(30,30,60));
        frame.setLocationRelativeTo(null);

        String[] imagePaths = {"bjp.png","brs.png","congress.png"};

        for(int i=0;i<candidates.length;i++){

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(30,30,60));

            ImageIcon icon = new ImageIcon(
                new ImageIcon(imagePaths[i]).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)
            );

            JButton btn = new JButton(icon);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel name = new JLabel(candidates[i], JLabel.CENTER);
            name.setForeground(Color.WHITE);
            name.setFont(new Font("Segoe UI", Font.BOLD, 14));

            int index = i;

            btn.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Confirm vote for " + candidates[index] + "?",
                    "Confirm Vote",
                    JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){
                    votes[index]++;
                    voted[currentUser] = true;

                    JOptionPane.showMessageDialog(frame,"Vote Submitted!");
                    frame.dispose();
                }
            });

            // Hover glow (no movement)
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBorder(null);
                }
            });

            panel.add(btn, BorderLayout.CENTER);
            panel.add(name, BorderLayout.SOUTH);

            frame.add(panel);
        }

        frame.setVisible(true);
    }

    // 📊 RESULTS
    static void showResults(){

        JFrame frame = new JFrame("Results");
        frame.setSize(350,300);
        frame.setLayout(new GridLayout(4,1,10,10));
        frame.getContentPane().setBackground(new Color(30,30,60));
        frame.setLocationRelativeTo(null);

        String[] imagePaths = {"bjp.png","brs.png","congress.png"};

        int max = votes[0];
        int winnerIndex = 0;

        for(int i=0;i<votes.length;i++){
            if(votes[i] > max){
                max = votes[i];
                winnerIndex = i;
            }
        }

        for(int i=0;i<candidates.length;i++){

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(new Color(30,30,60));

            ImageIcon icon = new ImageIcon(
                new ImageIcon(imagePaths[i]).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)
            );

            JLabel label = new JLabel(candidates[i] + " : " + votes[i], icon, JLabel.LEFT);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));

            panel.add(label);
            frame.add(panel);
        }

        JPanel winnerPanel = new JPanel();
        winnerPanel.setBackground(new Color(20,20,50));

        ImageIcon winIcon = new ImageIcon(
            new ImageIcon(imagePaths[winnerIndex]).getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)
        );

        JLabel winnerLabel = new JLabel("Winner: " + candidates[winnerIndex], winIcon, JLabel.CENTER);
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        winnerPanel.add(winnerLabel);
        frame.add(winnerPanel);

        frame.setVisible(true);
    }

    // 🎨 HELPERS

    static JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(0,123,255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    static JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    static JTextField createTextField(){
        JTextField field = new JTextField();
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        return field;
    }

    static JPasswordField createPasswordField(){
        JPasswordField field = new JPasswordField();
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        return field;
    }

    static JFrame createFormFrame(String title){
        JFrame frame = new JFrame(title);
        frame.setSize(350,250);
        frame.setLayout(new GridLayout(5,1,10,10));
        frame.getContentPane().setBackground(new Color(40,40,80));
        frame.setLocationRelativeTo(null);
        return frame;
    }
}