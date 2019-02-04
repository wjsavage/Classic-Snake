import javax.swing.*;
import java.awt.*;

public class Main{
    private JFrame window;
    private Gameplay gameplay;
    private Menu menu;

    private JPanel container;

    private CardLayout cardLayout= new CardLayout();

    private enum STATE{
        MENU,
        GAME
    }

    private STATE state;
    //enum for states
    //map for enum to frame
    //pass enum to frames with action listeners to enable change by constructors
    //make variables private and give a construcotr to instatiate everything
    public Main(){
        window  = new JFrame("Snake");
        container = new JPanel();

        gameplay = new Gameplay();
        menu = new Menu(container, cardLayout);

        state = STATE.MENU;

        init();
    }

    private void init(){
        window.setBounds(10, 10, 1000, 1000);
        window.setBackground(Color.DARK_GRAY);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*container.setLayout(cardLayout);
        container.add(menu, "MENU");
        container.add(gameplay, "GAMEPLAY");
        cardLayout.show(container, "MENU");
        */
        window.add(gameplay);


    }
    
    public static void main(String[] args){
        new Main();
    }
}
