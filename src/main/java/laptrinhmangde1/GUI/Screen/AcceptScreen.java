package laptrinhmangde1.GUI.Screen;

import laptrinhmangde1.Connection.Client;
import laptrinhmangde1.GUI.Main;

import javax.swing.*;
import java.awt.*;

/**
 * @author Loan (^._.^)ﾉ
 */
public class AcceptScreen extends JPanel {
    public JButton yes;
    public JButton no;
    JLabel title;
    String name = "";
    public AcceptScreen(){
        this.setLayout(null);
        this.setSize(400,210);
        title = new JLabel("Chấp nhận yêu cầu kết nối từ ");
        title.setFont(new Font(null, Font.PLAIN, 15));
        title.setBounds(50,30,320,40);
        yes = new JButton("YES");
        yes.setBounds(50,80,120,40);

        no = new JButton("NO");
        no.setBounds(230,80,120,40);

        add(title);
        add(yes);
        add(no);
    }


    public void setName(String name) {
        this.name = name;
        title.setText("Chấp nhận yêu cầu kết nối từ "+ name);
    }
}
