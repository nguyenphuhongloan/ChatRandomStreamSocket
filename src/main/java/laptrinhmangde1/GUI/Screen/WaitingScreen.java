package laptrinhmangde1.GUI.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * @author Loan (^._.^)ﾉ
 */
public class WaitingScreen extends JPanel {
    public JLabel lbImgWaiting;
    public JLabel lbTxtWaiting;
    public WaitingScreen(){
        setLayout(new FlowLayout());
        setSize(400,400);
        lbImgWaiting = new JLabel(new ImageIcon(getClass().getResource("/image/waiting (1).gif")));
        lbImgWaiting.setPreferredSize(new Dimension(300,300));
        lbTxtWaiting =  new JLabel("Hệ thống đang tìm bạn chat, vui lòng chờ 1 xíu");
        lbTxtWaiting.setFont(new Font(null,Font.PLAIN,17));
        this.add(lbImgWaiting);
        this.add(lbTxtWaiting);
    }

}
