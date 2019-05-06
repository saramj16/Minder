package User.View;

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

    private ArrayList<ClosedChat> chats;



    public View(User currentUser, User firstConnectedUser) {

        System.out.println("Entra a User");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0,0, WIDTH, HEIGHT);
        tabbedPane.add("Profile",getJpProfile(currentUser));
        tabbedPane.add("Matches",getJpMatches(firstConnectedUser));
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

        ImagePanel imgPanel = new ImagePanel("/koala.jpg");
        jpProfilePic.add(imgPanel);
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
        //Aqui van els chats tancats
        //jpMatches.add(scrollpaneChats);

        //Part dreta, on van els missatges d'una conversa
        JPanel jpChat = new JPanel(null);
        jpChat = showClosedChats(jpChat);
        scrollpaneChats.add(jpChat);

        jpMatches.add(scrollpaneChats);

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


    //public void showClosedChats(ClosedChat[] chats, ActionListener actionListener) {
    //public void showClosedChats(ClosedChat[] chats) {
    public JPanel showClosedChats(JPanel jpChat) {

        //jpChat.setLayout(new BoxLayout());
        //chats.clear();
        //jpChats.removeAll();

        User manel = new User(1234, "Manel", 22, true, "manel@gmail.com", "patata", "fotopolla.jpg","C++", "Salu2");

        for (int i = 0; i < 10; i++){
            //ClosedChat closedChat = new ClosedChat(new Mensaje(1234, "Hola que tal", manel ), actionListener);
            ClosedChat closedChat = new ClosedChat(new Mensaje(1234, "Hola que tal", manel ));
//            jpChats.add(closedChat);
            //chats.add(closedChat);
            System.out.println(i);
//            jpChats.add(closedChat);
        }

//        jpChats.validate();
  //      jpChats.repaint();

        return jpChat;
    }

    public void autenticationController(ActionListener controller){
        jbMatchYes.addActionListener(controller);
        jbMatchYes.setActionCommand("AcceptUser");

        jbMatchNo.addActionListener(controller);
        jbMatchNo.setActionCommand("DeclineUser");
    }
}
