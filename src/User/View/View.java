package User.View;

import Server.Model.Server;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {
    private final int WIDTH = 800;  //Amplada
    private final int HEIGHT = 600; //Alçada
    private JTabbedPane tabbedPane;
    private User userLooking;

    //Profile Pane and its elements
    private JPanel jpProfile;
    private JPanel jpImage;
    private JLabel jlName;
    private JLabel jlAge;
    private JLabel jlLanguage;
    private JLabel jlDescriptionTitle;
    private JLabel jlDescription;
    private JTextArea jtaDescription;
    private JButton jbEditProfile;

    //Edit Profile elements
    private JButton jbEditImage;
    private JLabel jlNameTitle;
    private JTextField jtName;
    private JLabel jlAgeTitle;
    private JComboBox jcYearBorn;
    private JLabel jlPasswordTitle;
    private JTextField jtPassword;

    //private JLabel jlDescriptionTitle;     //Already on jpProfile
    private JTextField jtDescritpion;
    private JLabel jlPremiumTitle;
    private JButton jbPremium;
    private JButton jbSave;


    //Matches Pane and its elements
    private JPanel jpMatches;
    private JPanel jpMatchImage;
    private JLabel jlMatchName;
    private JLabel jlMatchAge;
    private JLabel jlMatchLanguage;
    private JLabel jlMatchDescriptionTitle;
    private JLabel jlMatchDescription;
    private JButton jbMatchYes;
    private JButton jbMatchNo;

    //Small pane for closed conversations and its elements
    private JPanel jpClosedConver;
    private JPanel jpImageCC;
    private JLabel jlNameCC;
    private JLabel jlLastMessageCC;

    //Chats Pane and its elements
    private JPanel jpChats;
    private JScrollPane jspClosedChats;
    private JTextPane jtpChat;
    private JTextField jtfMessage;
    private JButton jbSend;
    JTextArea jtaMessages;

    private ArrayList<ClosedChat> chats;



    public View(User currentUser, User firstUser) {

        System.out.println("Entra a User");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0,0, WIDTH, HEIGHT);
        tabbedPane.add("Matches",getJpMatches(firstUser));
        tabbedPane.add("Profile",getJpProfile(currentUser));
        tabbedPane.add("Chats",getJpChats(currentUser));

        //chats = new ArrayList<>();
        ClosedChat[] chats = new ClosedChat[10];
        //chats[1] = new ClosedChat();
        //showClosedChats(chats, actionListener);
        for (int i = 0; i < 10; i++) {
            ClosedChat chat = new ClosedChat();
            chats[i] = chat;
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(tabbedPane);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        //this.setLayout(null);
        //this.setVisible(true);
    }

    public JPanel getJpProfile(User user) {
        jpProfile = new JPanel();
        jpProfile.setLayout(new GridLayout(1,2));

        //Panel Foto perfil
        JPanel jpProfilePic = new JPanel();
        jpProfilePic.setBackground(Color.BLUE);

        //ImagePanel imgPanel = new ImagePanel("/koala.jpg");
        //jpProfilePic.add(imgPanel);
        //imgPanel.paintComponent(new  );

        jpProfile.add(jpProfilePic);

        //Panel Info
        JPanel jpInfoProfile = new JPanel();
        jpInfoProfile.setLayout(new GridLayout(5,1));
        jlName = new JLabel(user.getUserName());
        jlName.setFont(new Font("Arial", Font.PLAIN, 50));
        jpInfoProfile.add(jlName);
        jlAge = new JLabel(String.valueOf(user.getEdat()));
        jlAge.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoProfile.add(jlAge);
        jlLanguage = new JLabel(user.getLenguaje());
        jlLanguage.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoProfile.add(jlLanguage);
        jtaDescription = new JTextArea(user.getDescription());
        jtaDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        jpInfoProfile.add(jtaDescription);
        jbEditProfile = new JButton("Editar Perfil");
        jbEditProfile.setFont(new Font("Arial", Font.PLAIN, 50));
        jpInfoProfile.add(jbEditProfile);

        jpProfile.add(jpInfoProfile);

        return jpProfile;
    }


    public JPanel getJpMatches(User user) {
        jpMatches = new JPanel();
        jpMatches.setLayout(new GridLayout(1,2));

        //Panel Foto perfil
        JPanel jpMatchPic = new JPanel();
        jpMatchPic.setBackground(Color.PINK );
        jpMatches.add(jpMatchPic);

        //general
        /*User user = users.remove(0);
        users.add(user);  */

        //Panel Info
        JPanel jpInfoMatch = new JPanel();

        jpInfoMatch.setLayout(new GridLayout(5, 1));
        jlName = new JLabel(user.getUserName());
        jlName.setFont(new Font("Arial", Font.PLAIN, 50));
        jpInfoMatch.add(jlName);
        jlAge = new JLabel(String.valueOf(user.getEdat()));
        jlAge.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoMatch.add(jlAge);
        jlLanguage = new JLabel(user.getLenguaje());
        jlLanguage.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoMatch.add(jlLanguage);
        jtaDescription = new JTextArea(user.getDescription());
        jtaDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        jpInfoMatch.add(jtaDescription);

        JPanel jpBotons = new JPanel(new GridLayout(1, 2));
        jbMatchYes = new JButton("Yes!");
        jbMatchYes.setFont(new Font("Arial", Font.PLAIN, 50));
        jpBotons.add(jbMatchYes);
        jbMatchNo = new JButton(("No"));
        jbMatchNo.setFont(new Font("Arial", Font.PLAIN, 50));
        jpBotons.add(jbMatchNo);

        jpInfoMatch.add(jpBotons);

        jpMatches.add(jpInfoMatch);

        return jpMatches;
    }

    public JPanel getJpChats(User user) {
        jpMatches = new JPanel(new GridLayout(1,2));

        JScrollPane scrollpaneChats = new JScrollPane();

        if(user.getListaMatch() == null){
            scrollpaneChats.add(new JLabel("Ningún Chat activo!"));
        }else {
            for (Match m : user.getListaMatch()){
                JLabel jpChat = new JLabel("chat with: " + m.getUser1());
                scrollpaneChats.add(jpChat);
            }
        }

        JPanel jRight = new JPanel(new GridLayout(2,1));
        jpMatches.add(scrollpaneChats);

        jtaMessages = new JTextArea();
        jRight.add(jtaMessages, BorderLayout.CENTER);

        JPanel jBot = new JPanel(new BorderLayout());
        jtfMessage = new JTextField();
        jBot.add(jtfMessage);
        jbSend = new JButton("Send");
        jBot.add(jbSend, BorderLayout.EAST);

        jRight.add(jBot);
        jpMatches.add(jRight);

        return jpMatches;
    }


    public void autenticationController(ActionListener controller){
        jbMatchYes.addActionListener(controller);
        jbMatchYes.setActionCommand("AcceptUser");

        jbMatchNo.addActionListener(controller);
        jbMatchNo.setActionCommand("DeclineUser");

        jbSend.addActionListener(controller);
        jbSend.setActionCommand("SendMessage");
    }

    public User getUserLooking() {
        return userLooking;
    }

    public void setUserLooking(User userLooking) {
        this.userLooking = userLooking;
    }

    public JTextField getJtfMessage() {
        return jtfMessage;
    }

    public void setJtfMessage(JTextField jtfMessage) {
        this.jtfMessage = jtfMessage;
    }

    public JTextArea getJtaMessages() {
        return jtaMessages;
    }

    public void setJtaMessages(JTextArea jtaMessages) {
        this.jtaMessages = jtaMessages;
    }
}
