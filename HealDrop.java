package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class HealDrop {
    double x;
    double y;
    double velocity;
    int height = 32;
    int width = 32;
    int healPoints = 4;
    int healerHealth = 2;
    Image healer;
    Random rand;
    HealDrop(int maxX,int minX ,double y,double velocity){
        rand = new Random();
        int randX = rand.nextInt(((maxX-width) - minX)+1)+minX;
        this.x = randX;
        this.y = y;
        this.velocity = velocity;
        healer = new ImageIcon(getClass().getResource("/SpaceGame/images/healer.png")).getImage();
    }

    public void draw(Graphics2D g){
        g.drawImage(healer, (int)x, (int)y,null);
    }

    public void move(){
        y += velocity;
    }
}
