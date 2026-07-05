package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class AmmoBuff {
    double x;
    double y;
    double velocity;
    int height = 64;
    int width = 49;
    int health = 6;
    Image ammoBuff;
    Random rand;
    AmmoBuff(int maxX,int minX ,double y,double velocity){
        rand = new Random();
        int randX = rand.nextInt(((maxX-width) - minX)+1)+minX;
        this.x = randX;
        this.y = y;
        this.velocity = velocity;
        ammoBuff = new ImageIcon(getClass().getResource("/SpaceGame/images/ammobuff.png")).getImage();
    }


    public void draw(Graphics2D g){
        g.drawImage(ammoBuff, (int)x, (int)y,null);
    }



    public void move(){
        y += velocity;
    }
}
