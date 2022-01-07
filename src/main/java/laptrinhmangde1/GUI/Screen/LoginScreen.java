package laptrinhmangde1.GUI.Screen;

import laptrinhmangde1.Connection.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author Loan (^._.^)ﾉ
 */
public class LoginScreen extends JPanel {

    public LoginScreen(){

        this.setLayout( new FlowLayout(FlowLayout.CENTER, 25,20));
        this.setBackground(Color.WHITE);
        //login.setPreferredSize(new Dimension(400,400));
        this.setBounds(0, 0, 400, 175);
        JLabel lbUsername = new JLabel("USERNAME");
        lbUsername.setFont(new Font(lbUsername.getFont().toString(),Font.BOLD,18));
        this.add(lbUsername);
        JTextField txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(300,30));
        this.add(txtUsername);
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setPreferredSize(new Dimension(120,30));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    String username = txtUsername.getText();
                    if(username.equals("")){
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập username");
                        return;
                    }
                    Client.connect();
                    Client.sendMessage(username);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        this.add(btnLogin);
    }
}
