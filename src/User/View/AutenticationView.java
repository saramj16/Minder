package User.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AutenticationView extends JFrame {
    private JPanel mainPanel;
    private TextField usernameTextField;
    private JPasswordField passwordTextField;
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
        JPanel jpAuthenticate = new JPanel(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(6, 1));
        usernameTextField = new TextField();
        passwordTextField = new JPasswordField();
        registerLabel = new JButton("Registra't!");
        logInButton = new JButton("Log in");

        JLabel jlLogin = new JLabel(" M I N D E R");
        jlLogin.setFont (jlLogin.getFont ().deriveFont (30.0f));


        mainPanel.add(new JLabel("  Username: "), CENTER_ALIGNMENT);
        mainPanel.add(usernameTextField, CENTER_ALIGNMENT);
       // mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel("  Password: "), CENTER_ALIGNMENT);
        mainPanel.add(passwordTextField, CENTER_ALIGNMENT);
        mainPanel.add(logInButton);
        mainPanel.add(registerLabel);

        mainPanel.setVisible(true);

        jpAuthenticate.add(jlLogin, BorderLayout.NORTH);
        jpAuthenticate.add(mainPanel, BorderLayout.CENTER);
        this.add(jpAuthenticate);
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
    public JPasswordField getPasswordTextField() { return passwordTextField; }
    public void setPasswordTextField(JPasswordField passwordTextField) { this.passwordTextField = passwordTextField; }
    public JButton getLogInButton() { return logInButton; }
    public void setLogInButton(JButton logInButton) { this.logInButton = logInButton; }
}
