package edu.itu.mat335e.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends Frame{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JPanel infoPanel;

    public LoginFrame(){
        super();
    }

    @Override
    public void setFrameSettings() {
        setTitle("Login");
        setMinimumSize(new Dimension(300, 150));
        setMaximumSize(new Dimension(300, 150));
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void initialize() {
        txtUsername = new JTextField("Username", 20);
        txtPassword = new JPasswordField("Password", 20);
        btnLogin = new JButton("Login");
        infoPanel = new JPanel();
    }

    @Override
    public void addSettings() {
        txtUsername.setForeground(Color.GRAY);
        txtPassword.setForeground(Color.GRAY);
        txtPassword.setEchoChar((char) 0);
        txtUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtUsername.getText().equals("Username")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setForeground(Color.GRAY);
                    txtUsername.setText("Username");
                }
            }
        });
        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(txtPassword.getPassword()).equals("Password")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                    txtPassword.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("Password");
                    txtPassword.setForeground(Color.GRAY);
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
    }

    @Override
    public void addListeners() {
        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(txtPassword.getPassword()).equals("Password")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                    txtPassword.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("Password");
                    txtPassword.setForeground(Color.GRAY);
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
        btnLogin.addActionListener(_ -> loginAction());
    }

    @Override
    public void setLayout() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    }

    @Override
    public void addComponents() {
        infoPanel.add(txtUsername);
        infoPanel.add(txtPassword);

        getContentPane().setFocusable(false);
        add(infoPanel);
        add(btnLogin);
        getContentPane().setFocusable(true);
    }

    public void loginAction() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.equals("root") && password.equals("1234"))
            openMainFrame("root");
        else if (username.equals("user") && password.equals("1234"))
            openMainFrame("user");
        else {
            JOptionPane.showMessageDialog(this,
                    "Username or password is incorrect!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openMainFrame(String role) {
        if (role.equals("root")) {
            AdminFrame rootFrame = new AdminFrame();
            rootFrame.setVisible(true);
        }
        else if (role.equals("user")) {
            UserFrame userFrame = new UserFrame();
            userFrame.setVisible(true);
        }
        this.dispose();
    }
}
