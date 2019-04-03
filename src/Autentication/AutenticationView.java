package Autentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AutenticationView extends JFrame {
    private JPanel mainPanel;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private JButton registerLabel;
    private JButton logInButton;

    public AutenticationView(){
        configuraVentana();
        configuraCentro();
    }

    private void configuraVentana(){
        this.setSize(400, 300);
        this.setTitle("MINDER");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void configuraCentro(){
        mainPanel = new JPanel(new GridLayout(7, 1));
        usernameTextField = new TextField();
        passwordTextField = new TextField();
        registerLabel = new JButton("¿Aún no tienes cuenta? REGISTRATE!");
        logInButton = new JButton("Log in");

        mainPanel.add(new JLabel("Username: "), CENTER_ALIGNMENT);
        mainPanel.add(usernameTextField, CENTER_ALIGNMENT);
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel("Password: "), CENTER_ALIGNMENT);
        mainPanel.add(passwordTextField, CENTER_ALIGNMENT);
        mainPanel.add(registerLabel);
        mainPanel.add(logInButton);

        mainPanel.setVisible(true);
        this.add(mainPanel);
    }

    public void autenticationController(ActionListener controller){
        logInButton.addActionListener(controller);
        logInButton.setActionCommand("logIn");

        registerLabel.addActionListener(controller);
        registerLabel.setActionCommand("RegisterFromAutentication");
    }


    public JPanel getMainPanel() { return mainPanel; }
    public void setMainPanel(JPanel mainPanel) { this.mainPanel = mainPanel; }
    public TextField getUsernameTextField() { return usernameTextField; }
    public void setUsernameTextField(TextField usernameTextField) { this.usernameTextField = usernameTextField; }
    public TextField getPasswordTextField() { return passwordTextField; }
    public void setPasswordTextField(TextField passwordTextField) { this.passwordTextField = passwordTextField; }
    public JButton getLogInButton() { return logInButton; }
    public void setLogInButton(JButton logInButton) { this.logInButton = logInButton; }
}
