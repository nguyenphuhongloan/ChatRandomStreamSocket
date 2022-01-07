/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptrinhmangde1.GUI;

import laptrinhmangde1.Connection.Client;
import laptrinhmangde1.GUI.Component.Header;
import laptrinhmangde1.GUI.Screen.AcceptScreen;
import laptrinhmangde1.GUI.Screen.ChatScreen;
import laptrinhmangde1.GUI.Screen.LoginScreen;
import laptrinhmangde1.GUI.Screen.WaitingScreen;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Loan (^._.^)ﾉ
 */
public class Main extends JFrame{
    private static LoginScreen login;
    public static ChatScreen main;
    private static WaitingScreen waiting;
    private static AcceptScreen acceptScreen;
    private static Header header;
    private static WaitingScreen waitingAccept;
    public int mouseX, mouseY;
    public String receiveName;
    public String username;
    ActionListener exit;
    public Main() throws HeadlessException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        this.setTitle("App chat");
        this.setLayout(new BorderLayout());
        this.setSize(400,210);
        setUndecorated(true);
        this.setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
        addComponent();
        this.setVisible(true);
        acceptScreen.yes.addActionListener(e -> {
            chooseYes(receiveName);
        });
        acceptScreen.no.addActionListener(e -> {
            chooseNo("");
        });

    }
    private void initComponent(){
        header = new Header();
        login = new LoginScreen();
        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - mouseX, getY() + e.getY() - mouseY);
            }
        });
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        exit = e -> {
        };
        header.btnExit.addActionListener(exit);
        changeExitButonListenerWithTwoButton();
        header.btnMinimaze.addActionListener(e -> {
            setState(JFrame.ICONIFIED);
        });

        main  = new ChatScreen("");
        waiting = new WaitingScreen();
        acceptScreen = new AcceptScreen();
        waitingAccept = new WaitingScreen();
    }
    private void addComponent(){
        this.add(login, BorderLayout.CENTER);
        this.add(header, BorderLayout.NORTH);
    }

    public void chooseYes(String name){
        Client.sendMessage("ACCEPT");
        waitingScreen(name);
        repaint();
        revalidate();
    }
    public void chooseNo(String name){
        System.out.println("Sent refuse to "+ name);
        Client.sendMessage("REFUSE");
    }
    public void waitingScreen(){
        clearComponent();
        this.setLocationRelativeTo(null);
        changeExitButonListenerWithThreeButton();
        waiting.lbTxtWaiting.setText("Hệ thống đang tìm bạn chat, vui lòng chờ 1 xíu");
        this.setSize(400,400);
        this.add(waiting);
        repaint();
        revalidate();

    }


    public void waitingScreen(String name){
        System.out.println("SHOW WAITING");
        this.receiveName = name;
        clearComponent();
        this.setSize(400,400);
        changeExitButonListenerWithThreeButton();
        this.setLocationRelativeTo(null);
        waitingAccept.lbTxtWaiting.setText("Đang chờ "+ name + " chấp nhận");
        this.add(waitingAccept);
        repaint();
        revalidate();
        System.out.println("DONE show waiting");
    }

    public void showChatScreen(String name){
        System.out.println("SHOW CHAT");
        clearComponent();
        waitingAccept.setVisible(false);
        this.setSize(400,740);
        this.setLocationRelativeTo(null);
        changeExitButonListenerWithThreeButton();
        main.setReceiverName(name);
        main.clearHistory();
        add(main, BorderLayout.CENTER);
        repaint();
        revalidate();
        System.out.println("DONE SHOW CHAT");


       // System.out.println("REMOVED");
    }

    public void returnToLoginScreen(){
        clearComponent();
        this.setSize(400,210);
        this.setLocationRelativeTo(null);
        this.add(login);

        changeExitButonListenerWithTwoButton();

        repaint();
        revalidate();

    }

    public void showacceptScreen(String name){
        this.receiveName = name;
        clearComponent();
        changeExitButonListenerWithThreeButton();
        this.setSize(400,210);
        this.setLocationRelativeTo(null);
        acceptScreen.setName(name);
        this.add(acceptScreen, BorderLayout.CENTER);
        this.setSize(400,210);
        this.setLocationRelativeTo(null);
        repaint();
        revalidate();
    }

    public void changeExitButonListenerWithThreeButton(){
        header.btnExit.removeActionListener(exit);
        exit = e -> {
            Object[] options1 = {"Thoát chương trình",
                    "Thoát về trang đăng nhập",
                    "Huỷ"};
            int chooseExit = JOptionPane.showOptionDialog(null,
                    "Bạn có chắc muốn thoát chương trình",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options1,
                    null);
            if(chooseExit == JOptionPane.YES_OPTION){
                Client.sendMessage("CLIENT_EXIT");
                Client.disConnect();
                System.exit(0);
            }
            if(chooseExit == JOptionPane.NO_OPTION){
                Client.sendMessage("CLIENT_EXIT");
                returnToLoginScreen();
            }
        };
        header.btnExit.addActionListener(exit);
    }
    public void changeExitButonListenerWithTwoButton(){
        header.btnExit.removeActionListener(exit);
        exit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options1 = {"Thoát chương trình",
                        "Huỷ"};
                int chooseExit = JOptionPane.showOptionDialog(null,
                        "Bạn có chắc muốn thoát chương trình",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options1,
                        null);
                if(chooseExit == JOptionPane.YES_OPTION){
                    Client.sendMessage("EXIT");
                    Client.disConnect();
                   System.exit(0);
                }

            }
        };
        header.btnExit.addActionListener(exit);
    }
    public void clearComponent(){
        if(login != null)
            remove(login);
        if(main != null)
            remove(main);
        if(acceptScreen != null)
            remove(acceptScreen);
        if(waiting != null){
            waiting.setVisible(true);
            remove(waiting);
        }
        if(waitingAccept != null){
            waitingAccept.setVisible(true);
            remove(waitingAccept);
        }
    }
}
