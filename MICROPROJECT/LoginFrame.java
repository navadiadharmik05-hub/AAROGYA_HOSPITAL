package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginFrame() {
        Theme.initLookAndFeel();

        setTitle("Aarogya Hospital Management System - Admin Login");
        setSize(860, 520);
        setMinimumSize(new Dimension(800, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Theme.BG);
        setLayout(new GridLayout(1, 2));

        // Left Panel: Hospital Architectural Image Banner
        JPanel leftBanner = Theme.createHospitalImagePanel();
        add(leftBanner);

        // Right Panel: Form Container Card
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setBackground(Theme.BG);

        JPanel card = new JPanel(new BorderLayout(0, 16));
        card.setBackground(Theme.CLEAN_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                Theme.rounded(Theme.SKY_BLUE, 12),
                BorderFactory.createEmptyBorder(28, 30, 28, 30)));
        card.setPreferredSize(new Dimension(350, 410));

        // Header Panel (Logo, Welcome Admin, Subtitle)
        JPanel topBox = new JPanel();
        topBox.setLayout(new BoxLayout(topBox, BoxLayout.Y_AXIS));
        topBox.setOpaque(false);

        JLabel lblLogo = new JLabel(Theme.createLogoIcon(52));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Welcome Admin");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.DEEP_NAVY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Aarogya Hospital System Portal");
        lblSubtitle.setFont(Theme.FONT_SUBTITLE);
        lblSubtitle.setForeground(Theme.TEXT_SECONDARY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        topBox.add(lblLogo);
        topBox.add(Box.createRigidArea(new Dimension(0, 8)));
        topBox.add(lblTitle);
        topBox.add(Box.createRigidArea(new Dimension(0, 2)));
        topBox.add(lblSubtitle);

        card.add(topBox, BorderLayout.NORTH);

        // Form Panel (Left-aligned labels, full-width inputs)
        JPanel formBox = new JPanel();
        formBox.setLayout(new BoxLayout(formBox, BoxLayout.Y_AXIS));
        formBox.setOpaque(false);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(Theme.FONT_LABEL);
        lblUser.setForeground(Theme.DEEP_NAVY);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = Theme.placeholderField("Enter admin username");
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Input Parameter Filter: Text/Letters only
        Theme.addAlphabetOnlyFilter(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(Theme.FONT_LABEL);
        lblPass.setForeground(Theme.DEEP_NAVY);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        Theme.styleField(txtPassword);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setEchoChar('\u2022');

        formBox.add(lblUser);
        formBox.add(Box.createRigidArea(new Dimension(0, 4)));
        formBox.add(txtUsername);
        formBox.add(Box.createRigidArea(new Dimension(0, 12)));
        formBox.add(lblPass);
        formBox.add(Box.createRigidArea(new Dimension(0, 4)));
        formBox.add(txtPassword);

        card.add(formBox, BorderLayout.CENTER);

        // Action Panel (Error message + Log In Button)
        JPanel bottomBox = new JPanel();
        bottomBox.setLayout(new BoxLayout(bottomBox, BoxLayout.Y_AXIS));
        bottomBox.setOpaque(false);

        lblError = new JLabel(" ");
        lblError.setFont(Theme.FONT_SUBTITLE);
        lblError.setForeground(Theme.DANGER);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnLogin = Theme.primaryButton("Log In");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogin.setPreferredSize(new Dimension(290, 40));
        btnLogin.setToolTipText("Log in (Enter)");
        btnLogin.addActionListener(e -> attemptLogin());

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) attemptLogin();
            }
        });
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) attemptLogin();
            }
        });

        bottomBox.add(lblError);
        bottomBox.add(Box.createRigidArea(new Dimension(0, 6)));
        bottomBox.add(btnLogin);

        card.add(bottomBox, BorderLayout.SOUTH);

        rightContainer.add(card);
        add(rightContainer);
    }

    private void attemptLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            lblError.setText(" ");
            dispose();
            SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
        } else {
            lblError.setText("Invalid username or password.");
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
