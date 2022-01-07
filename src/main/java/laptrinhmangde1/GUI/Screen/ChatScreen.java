package laptrinhmangde1.GUI.Screen;

import laptrinhmangde1.Connection.Client;
import laptrinhmangde1.GUI.Component.ReceiveComponent;
import laptrinhmangde1.GUI.Component.SendComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Loan (^._.^)ﾉ
 */
public class ChatScreen extends JPanel{
    private JPanel header;
    private JPanel footer;
    private ImageIcon back;
    private JLabel lbUsername;
    private  JTextField txtSend;
    private JScrollPane scroll;
    private JButton btnSend;
    private JButton btnBack;
    private String name;
    private JPanel message;
    public ChatScreen(String receiverUserName){
        this.setLayout(new BorderLayout());
        this.setSize(400,700);
        this.name = receiverUserName;
        initComponent();
        addComponent();

    }
    private void initComponent(){
        header = new JPanel();
        header.setLayout(null);
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(400, 50));
        back = new ImageIcon(getClass().getResource("/image/back.png"));
        lbUsername = new JLabel();
        lbUsername.setText(name);
        lbUsername.setHorizontalAlignment(JLabel.CENTER);
        lbUsername.setFont(new Font(null, Font.PLAIN,14));
        lbUsername.setBounds(50,0,300,50);
        header.add(lbUsername);

        footer = new JPanel();
        footer.setLayout(null);
        footer.setPreferredSize(new Dimension(400, 60));
        footer.setBackground(Color.white);
        txtSend = new JTextField();
        txtSend.setBounds(10,10,300,40);
        btnSend = new JButton("SEND");
        btnSend.setBackground(new Color(127, 255, 212));
        btnSend.setBounds(320,10,60, 40);
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String send = txtSend.getText();
                if(send.equals("")){
                    JOptionPane.showMessageDialog(null,"Vui lòng nhập tin nhắn");
                    return;
                }
                Client.sendMessageToReceiver(send);
                SendComponent sendComponent = new SendComponent(send);
                message.add(sendComponent);
                txtSend.setText("");
                message.revalidate();
                scroll.revalidate();
                repaint();
                revalidate();
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );
            }
        });
        footer.add(txtSend);
        footer.add(btnSend);

        message = new JPanel();
        //message.setBackground(Color.red);
        message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));

        scroll =new JScrollPane(message);
        scroll.setPreferredSize(new Dimension(400, 590));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(Color.cyan);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        btnBack = new JButton(new ImageIcon(getClass().getResource("/image/logout.png")));
        btnBack.setBounds(10,10,32,32);
        //header.add(btnBack);
    }
    private void addComponent(){
        this.add(header,BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(scroll, BorderLayout.CENTER);
    }
    public void showReceiveMessage(String msg){
        message.add(new ReceiveComponent(msg));
        message.revalidate();
        scroll.revalidate();
        repaint();
        revalidate();
        JScrollBar vertical = scroll.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
    }
    public void setReceiverName(String name){
        this.name = name;
        lbUsername.setText(name);
    }

    public void clearHistory(){
        message.removeAll();
    }

}
