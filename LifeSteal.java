package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class LifeSteal {
    double x;
    double y;
    double velocity;
    int height = 64;
    int width = 64;
    int health = 7;
    Image lifeSteal;
    Random rand;
    LifeSteal(int maxX,int minX ,double y,double velocity){
        rand = new Random();
        int randX = rand.nextInt(((maxX-width) - minX)+1)+minX;
        this.x = randX;
        this.y = y;
        this.velocity = velocity;
        lifeSteal = new ImageIcon(getClass().getResource("/SpaceGame/images/lifesteal1.png")).getImage();

    }

    public void draw(Graphics2D g){
        g.drawImage(lifeSteal, (int)x, (int)y,null);
    }

    public void move(){
        y += velocity;
    }
}
