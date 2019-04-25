package User.View;

import User.Model.User;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final int WIDTH = 800;  //Amplada
    private final int HEIGHT = 600; //Alçada
    private JTabbedPane tabbedPane;


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



    public View(User user) {

        System.out.println("Entra a User");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0,0, WIDTH, HEIGHT);
        tabbedPane.add("Profile",getJpProfile(user));
        tabbedPane.add("Matches",getJpMatches(user));
        tabbedPane.add("Chats",getJpChats(user));

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
        jpProfile.add(jpProfilePic);

        //Panel Info
        JPanel jpInfoProfile = new JPanel();
        jpInfoProfile.setLayout(new GridLayout(5,1));
        jlName = new JLabel("Manel López");
        jlName.setFont(new Font("Arial", Font.PLAIN, 50));
        jpInfoProfile.add(jlName);
        jlAge = new JLabel("22");
        jlAge.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoProfile.add(jlAge);
        jlLanguage = new JLabel("Java");
        jlLanguage.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoProfile.add(jlLanguage);
        jtaDescription = new JTextArea("Lorem ipsum noseque.");
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

        //Panel Info
        JPanel jpInfoMatch = new JPanel();

        jpInfoMatch.setLayout(new GridLayout(5,1));
        jlName = new JLabel("Sara Martí ");
        jlName.setFont(new Font("Arial", Font.PLAIN, 50));
        jpInfoMatch.add(jlName);
        jlAge = new JLabel("20");
        jlAge.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoMatch.add(jlAge);
        jlLanguage = new JLabel("Java");
        jlLanguage.setFont(new Font("Arial", Font.PLAIN, 40));
        jpInfoMatch.add(jlLanguage);
        jtaDescription = new JTextArea("Lorem ipsum mimimi.");
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
        //Aqui van els chats tancats
        jpMatches.add(scrollpaneChats);

        //Part dreta, on van els missatges d'una conversa
        JPanel jpChat = new JPanel(null);

        JTextArea jtaMessages = new JTextArea("Javo: Como van las vistas \nManel: De puta madre\nJavo: haberlas");
        jtfMessage = new JTextField(30);
        jbSend = new JButton("Send");

        jpChat.add(jtaMessages);
        jpChat.add(jtfMessage);
        jpChat.add(jbSend);

        Insets insets = jpChat.getInsets();
        Dimension sizeMessages = jtaMessages.getPreferredSize();
        jtaMessages.setBounds(5 + insets.left, 5 + insets.top, sizeMessages.width, sizeMessages.height);

        Dimension sizeJtfMessage = jtfMessage.getPreferredSize();
        jtfMessage.setBounds(5 + insets.left, 500 + insets.top, sizeJtfMessage.width, sizeJtfMessage.height);

        Dimension sizeJbSend = jbSend.getPreferredSize();
        jbSend.setBounds(320 + insets.left, 500 + insets.top, sizeJbSend.width, sizeJbSend.height);

        jpMatches.add(jpChat);

        return jpMatches;
    }
}