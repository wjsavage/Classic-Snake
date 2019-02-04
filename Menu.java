import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel{
    public Menu(JPanel panel, CardLayout cardLayout){
        setBackground(Color.green);
        setLayout(new FlowLayout());


        JButton screenSwitch = new JButton();
        screenSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "GAMEPLAY");
            }
        });
        add(screenSwitch);
    }

    public void paint(Graphics g){
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.setColor(Color.white);

        g.drawString("SNAKE", 400, 100);
    }

}
