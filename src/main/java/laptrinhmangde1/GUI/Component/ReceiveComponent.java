package laptrinhmangde1.GUI.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author Loan (^._.^)ﾉ
 */
public class ReceiveComponent extends JPanel {
    String message;
    StringBuilder str = new StringBuilder();
    private JLabel lbMessage;
    public ReceiveComponent(String message) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //this.setPreferredSize(new Dimension(380,80));
        this.setBackground(Color.orange);
        this.message = message;

        lbMessage = new JLabel();
        str = str.append("<html><div ").append(DefaultStyle.receiveContainerChat).append(" >").append(message).append("</div></html>");
        lbMessage.setText(str.toString());
        double height = lbMessage.getPreferredSize().getHeight();
        double width = lbMessage.getPreferredSize().getWidth();

        if(width>340){
            lbMessage.setPreferredSize(new Dimension(340,80));
        } else{
            lbMessage.setPreferredSize(new Dimension(340, (int) height));
        }


        lbMessage.setOpaque(true);
        this.add(lbMessage);


    }



    @Override
    public String toString() {
        return str.toString();
    }
}
