import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private ImageIcon sideLogo;
    private ArrayList<Position> snake = new ArrayList<>();
    private Position foodPos;

    private boolean isPlaying, isAlive;

    private Random rand = new Random();
    
    private boolean left= false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private Set<Integer> keySet = new HashSet<>();

    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private ImageIcon snakeImage;
    private ImageIcon foodImage;
    private ImageIcon tailUp;
    private ImageIcon tailDown;
    private ImageIcon tailLeft;
    private ImageIcon tailRight;

    private Timer timer;
    private int delay = 100;

    private int score = 0;
    private int moves = 0;

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
        foodPos = generateFoodPos();

        if(moves == 0){
            resetSnake();
        }

        sideLogo = new ImageIcon("SideLogo.png");
        rightMouth = new ImageIcon("rightmouth.png");
        leftMouth = new ImageIcon("leftmouth.png");
        upMouth = new ImageIcon("upmouth.png");
        downMouth = new ImageIcon("downmouth.png");
        snakeImage = new ImageIcon("snakeimage.png");
        foodImage = new ImageIcon("food.png");
        tailUp = new ImageIcon("upTail.png");
        tailDown= new ImageIcon("downTail.png");
        tailLeft= new ImageIcon("leftTail.png");
        tailRight = new ImageIcon("rightTail.png");

        keySet.add(KeyEvent.VK_DOWN);
        keySet.add(KeyEvent.VK_UP);
        keySet.add(KeyEvent.VK_RIGHT);
        keySet.add(KeyEvent.VK_LEFT);

    }

    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(49,24,202,902);
        g.fillRect(274,24,677,902);

        g.setColor(new Color(76,153,0));
        g.fillRect(50, 25 ,200, 900);
        sideLogo.paintIcon(this, g, 50, 35);

        g.setColor(Color.BLACK);
        g.fillRect(275, 25, 675, 900);

        g.setColor(Color.DARK_GRAY);
        for(int i = 275; i < 950; i+=25){
            g.fillRect(i, 25, 1, 900);
        }
        for(int i = 25; i < 925; i+=25){
            g.fillRect(275, i, 675, 1);
        }

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 25));
        g.drawString("Score: " +score, 105, 800);
        g.drawString("Length: " + snake.size(), 100, 825);

        foodImage.paintIcon(this,g,foodPos.getX(), foodPos.getY());

        for(int a = 0; a < snake.size(); a++){
            if(a==0 && right){
                rightMouth.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
            }
            else if(a==0 && left){
               leftMouth.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
            }

            else if(a==0 && up){
                upMouth.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
            }

            else if(a==0 && down){
                downMouth.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
            }

            else if(a == snake.size()-1){
                if(snake.get(a).getY() == snake.get(a-1).getY()){
                    if(snake.get(a).getX() < snake.get(a-1).getX()){
                        tailRight.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
                    } else{
                        tailLeft.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
                    }
                }else if(snake.get(a).getX() == snake.get(a-1).getX()){
                    if(snake.get(a).getY() < snake.get(a-1).getY()){
                        tailDown.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
                    } else{
                        tailUp.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY());
                    }
                }
            }
            else if(a!=0){ snakeImage.paintIcon(this, g, snake.get(a).getX(), snake.get(a).getY()); }
        }


        if(!isAlive){
            int finalScore = score, finalLength = snake.size();
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.setColor(Color.white);

            g.drawString("GAME OVER", 475, 400);
            g.setFont(new Font("arial", Font.BOLD, 25));

            g.drawString("PRESS SPACE BAR TO RESTART", 435, 425);
            g.drawString("Final Score: " + finalScore + "   Final Length: " + finalLength, 455, 465);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(isPlaying) {
            for(int b= 1; b< snake.size(); b++){
                if(snake.get(b).getX() == snake.get(0).getX() && snake.get(b).getY() == snake.get(0).getY()){
                    isAlive = false;
                    repaint();
                    isPlaying = false;

                }
            }
            if(snake.size() > 0 && foodPos.getX() == snake.get(0).getX() && foodPos.getY() == snake.get(0).getY()){
                score+=4;
                foodPos = generateFoodPos();
                snake.add(new Position(snake.get(snake.size()-1).getX(), snake.get(snake.size()-1).getY()));
                repaint();
            }

            if (right) {
                if (snake.get(0).getX() == 925) {
                    snake.add(0, new Position(275 , snake.get(0).getY()) );
                }
                else {
                    snake.add(0, new Position(snake.get(0).getX() + 25, snake.get(0).getY()));
                }
            }
            else if (left) {
                if (snake.get(0).getX() == 275) {
                    snake.add(0, new Position(925, snake.get(0).getY()) );
                }
                else {
                    snake.add(0, new Position(snake.get(0).getX() - 25, snake.get(0).getY()));
                }
            }
            else if (up) {
                if (snake.get(0).getY() == 25) {
                    snake.add(0, new Position(snake.get(0).getX(), 900 ));
                }
                else {
                    snake.add(0, new Position(snake.get(0).getX(), snake.get(0).getY() - 25));
                }
            }
            else if (down) {
                if (snake.get(0).getY() == 900) {
                    snake.add(0, new Position(snake.get(0).getX(), 25 ));
                }
                else {
                    snake.add(0, new Position(snake.get(0).getX(), snake.get(0).getY() + 25));
                }
            }
            if(right || left || down || up) {
                snake.remove(snake.size() - 1);
            }
            repaint();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            resetSnake();
            repaint();
        }

        else{
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                right = true;
                if(!left){
                    right = true;
                }
                else{
                    right = false;
                    left = true;
                }
                up = false;
                down = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                left = true;
                if(!right){
                    left = true;
                }
                else{
                    right = true;
                    left = false;
                }
                up = false;
                down = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_UP){
                up = true;
                if(!down){
                    up = true;
                }
                else{
                    down = true;
                    up = false;
                }
                left = false;
                right = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = true;
                if (!up) {
                    down = true;
                } else {
                    down = false;
                    up = true;
                }
                left = false;
                right = false;
            }

            if(keySet.contains(e.getKeyCode())){
                moves++;
                score--;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    private void resetSnake(){
        snake.clear();
        snake.add(new Position(625,425));
        snake.add(new Position(625,400));
        snake.add(new Position(625,375));

        isPlaying = true;
        isAlive = true;

        down = true;
        up = false;
        right = false;
        left = false;
    }

    private Position generateFoodPos(){
        int x = rand.nextInt(26) + 11;
        int y = rand.nextInt(36) + 1;
        return new Position(x*25, y*25);
    }
}
