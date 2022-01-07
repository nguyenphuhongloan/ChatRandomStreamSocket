package laptrinhmangde1.GUI.Component;

import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.prefs.BackingStoreException;

/**
 * @author Loan (^._.^)ﾉ
 */
public class Header extends JPanel{
    public JButton btnMinimaze;
    public JButton btnExit;
    public Header(){
        setPreferredSize(new Dimension(400, 32));
        setLayout(null);
        setBackground(new Color(0, 178, 238	));
        btnExit  = new JButton(new ImageIcon(getClass().getResource("/image/close.png")));
        btnMinimaze = new JButton(new ImageIcon(getClass().getResource("/image/minimize.png")));
        btnMinimaze.setBounds(320,4,24,24);

        btnMinimaze.setFocusPainted(false);
        btnMinimaze.setBackground(null);

        btnExit.setBounds(360,4,24,24);
        btnExit.setFocusPainted(false);
        btnExit.setBackground(null);
        add(btnExit);
        add(btnMinimaze);
    }

}
