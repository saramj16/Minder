package View;

import User.Model.User;

import javax.swing.*;

public class View extends JFrame {
    private JTabbedPane tabbedPane;

    //Profile Pane and its elements
    private JPanel jpProfile;
    private JPanel jpImage;
    private JLabel jlName;
    private JLabel jlAge;
    private JLabel jlLanguage;
    private JLabel jlDescriptionTitle;
    private JLabel jlDescription;
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

    }
}
