package User.View;

import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class View extends JFrame {
    private final int WIDTH = 400;  //Amplada
    private final int HEIGHT = 300; //Alçada
    private JTabbedPane tabbedPane;
    private User userLooking;
    private User chatUser;

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
    private JButton jbLogOut;

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
    private JLabel jlTitleMatches;
    private JPanel jpMatchImage;
    private JLabel jlMatchName;
    private JLabel jlMatchAge;
    private JLabel jlMatchLanguage;
    private JLabel jlMatchDescriptionTitle;
    private JLabel jlMatchDescription;
    private JButton jbMatchYes = new JButton("Yes!");;
    private JButton jbMatchNo = new JButton(("Nope"));
    private JButton jbRefresca = new JButton("Refresh!");

    //Small pane for closed conversations and its elements
    private JPanel jpClosedConver;
    private JPanel jpImageCC;
    private JLabel jlNameCC;
    private JLabel jlLastMessageCC;


    //Chats Pane and its elements
    public  JTextArea ta;
    private JPanel jpChats;
    private JScrollPane jspClosedChats;
    private JTextPane jtpChat;
    private JTextField jtfMessage;
    private JButton jbSend;
    public JPanel jtaMessages;

    private JButton[] panels;

    private ArrayList<ClosedChat> chats;



    public View(User currentUser, User firstUser) throws IOException {

        this.setTitle(" M I N D E R ");
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
    }

    public JPanel getJpProfile(User user) throws IOException {
        jpProfile = new JPanel();
        jpProfile.setLayout(new GridLayout(1,2));

        //Panel Foto perfil
        JPanel jpProfilePic = new JPanel();
        //jpProfilePic.setBackground(Color.BLUE);
        Image image = new ImagePanel().ImagePath(user.getUrlFoto());
        JLabel imgPanel = new JLabel(new ImageIcon(image.getScaledInstance(300, 250, Image.SCALE_SMOOTH)));
        jpProfilePic.add(imgPanel);

        jpProfile.add(jpProfilePic);

        //Panel Info
        JPanel jpInfoProfile = new JPanel();
        jpInfoProfile.setLayout(new BorderLayout());

        JPanel jpGrid = new JPanel(new GridLayout(3,2));
        jlName = new JLabel(user.getUserName());
        jlAge = new JLabel(String.valueOf(user.getEdat())+" anys");
        JLabel jlLlenguatgePref = new JLabel("Llenguatge:");
        jlLanguage = new JLabel(user.getLenguaje());
        JLabel jlDescTitle = new JLabel("Descripció:");
        jpGrid.add(jlName);
        jpGrid.add(jlAge);
        jpGrid.add(jlLlenguatgePref);
        jpGrid.add(jlLanguage);
        jpGrid.add(jlDescTitle);

        jtaDescription = new JTextArea(user.getDescription());
        jtaDescription.setEditable(false);
        JScrollPane jspDesc = new JScrollPane(jtaDescription);

        JPanel jpButtons = new JPanel();
        jbEditProfile = new JButton("Editar Perfil");
        jbLogOut = new JButton("Log out");
        jpButtons.add(jbEditProfile);
        jpButtons.add(jbLogOut);

        jpInfoProfile.add(jpGrid, BorderLayout.NORTH);
        //jpInfoProfile.add(jtaDescription, BorderLayout.CENTER);
        jpInfoProfile.add(jspDesc, BorderLayout.CENTER);

        jpInfoProfile.add(jpButtons, BorderLayout.SOUTH);


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

        if (user != null){
            jpInfoMatch.setLayout(new BorderLayout());
            JPanel jpNorth = new JPanel(new GridLayout(3,2));
            jlName = new JLabel(user.getUserName());
            jpNorth.add(jlName);
            jlAge = new JLabel(String.valueOf(user.getEdat()) + " anys");
            jpNorth.add(jlAge);
            JLabel jlLlenguatge = new JLabel("Llenguatge: ");
            jpNorth.add(jlLlenguatge);
            jlLanguage = new JLabel(user.getLenguaje());
            jpNorth.add(jlLanguage);
            JLabel jlDescTitle = new JLabel("Descripció: ");
            jpNorth.add(jlDescTitle);

            jpInfoMatch.add(jpNorth, BorderLayout.NORTH);

            jtaDescription = new JTextArea(user.getDescription());
            jtaDescription.setEditable(false);
            JScrollPane jspDesc = new JScrollPane(jtaDescription);
            jpInfoMatch.add(jspDesc, BorderLayout.CENTER);

            JPanel jpBotons = new JPanel();

            jpBotons.add(jbMatchYes);
            jpBotons.add(jbMatchNo);

            jpInfoMatch.add(jpBotons, BorderLayout.SOUTH);

            jpMatches.add(jpInfoMatch);

            return jpMatches;
        }else {
            JPanel jpTitledMatches = new JPanel(new BorderLayout());
            JPanel jpTensUnMatch = new JPanel(new FlowLayout());

            jlTitleMatches = new JLabel("No queden usuaris... Torna en una estona!");

            jpTensUnMatch.add(jlTitleMatches);
            jpTensUnMatch.add(jbRefresca);

            jpTitledMatches.add(jpTensUnMatch, BorderLayout.NORTH);

            return jpTitledMatches;
        }
    }

    public JPanel getJpChats(User user) {
        jpMatches = new JPanel(new GridLayout(1,2));

        JPanel jpScroll = new JPanel(new GridLayout(user.getListaMatch().size(),1));
        JScrollPane scrollpaneChats = new JScrollPane(jpScroll);

        if(user.getListaMatch().size() == 0 || user.getListaMatch() == null){
            jpScroll.add(new JLabel("Ningún Chat activo!"), BorderLayout.CENTER);
            jpMatches.add(jpScroll);
        }else {
            panels = new JButton[user.getListaMatch().size()];
            for (int i = 0; i < user.getListaMatch().size(); i++){
               panels[i] = new JButton("Chat with --> " + user.getListaMatch().get(i).getUser2().getUserName());
               jpScroll.add(panels[i]);
            }

            jpMatches.add(jpScroll);
            //JButton jLabel = new JButton("chat with: " + m.getUser1());

        }

        JPanel jRight = new JPanel(new BorderLayout());

        ta = new JTextArea();
        ta.setEditable(false);
        jtaMessages = new JPanel(new GridLayout(1,1));
        jtaMessages.add(new JScrollPane(ta));
        ta.setEditable(false);
        jRight.add(jtaMessages, BorderLayout.CENTER);

        JPanel jBot = new JPanel(new BorderLayout());
        jtfMessage = new JTextField();
        jBot.add(jtfMessage, BorderLayout.CENTER);
        jbSend = new JButton("Send");
        jBot.add(jbSend, BorderLayout.EAST);

        jRight.add(jBot, BorderLayout.SOUTH);
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

        jbEditProfile.addActionListener(controller);
        jbEditProfile.setActionCommand("EditProfile");

        jbRefresca.addActionListener(controller);
        jbRefresca.setActionCommand("Refresh");

        jbLogOut.addActionListener(controller);
        jbLogOut.setActionCommand("LogOut");

        if (panels != null && panels.length > 0){
            for (int i = 0; i < panels.length; i++){
               // User user = mainUser.getListaMatch().get(i).getUser2();
                panels[i].addActionListener(controller);
                panels[i].setActionCommand("Chat " + i);
            }
        }
    }

    public void noQuedenMatches() {
        jlTitleMatches.setText(" No queda gent amb qui emparellar-te :(   ");
        jpMatches.setVisible(false);
        jbRefresca.setVisible(true);

    }

    public void tornaAHaverHiMatches() {
        jlTitleMatches.setText(" Tens un possible match!");
        jpMatches.setVisible(true);
        jbRefresca.setVisible(false);
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

    public JPanel getJtaMessages() {
        return jtaMessages;
    }

    public void setJtaMessages(JPanel jtaMessages) {
        this.jtaMessages = jtaMessages;
    }

    public JTextArea getTa() {
        return ta;
    }

    public void setTa(JTextArea ta) {
        this.ta = ta;
    }
}
