package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Enemy {
    double x;
    double y;
    double velocity = 1.5;
    double vX;
    double vY;
    Image rocket;
    Player player;
    double relX;
    double relY;
    double angle;
    int centerX;
    int centerY;
    double MaxHealth = 5;
    double CurrentHealth;
    int width = 64;
    int height = 64;
    int magicAngle = 45;
    int damage = 8;
    Image rocket1;
    Image rocket2;
    Image rocket3;
    Enemy(Player player,double x,double y,double incHealth,double incSpeed){
        this.player = player;
        this.x = x;
        this.y = y;
        this.MaxHealth += incHealth;
        this.CurrentHealth = MaxHealth;
        this.velocity += incSpeed;
        rocket1 = new ImageIcon(getClass().getResource("/SpaceGame/images/rocket1.png")).getImage();
        rocket2 = new ImageIcon(getClass().getResource("/SpaceGame/images/rocket2.png")).getImage();
        rocket3 = new ImageIcon(getClass().getResource("/SpaceGame/images/rocket3.png")).getImage();
        centerX = (int)x + (width/2);
        centerY = (int)y + (height/2);
    }

    public void UpdateAngle(){
        relX = player.centerX - (x+width/2);
        relY = player.centerY - (y+height/2);
        angle = Math.toDegrees(Math.atan2(relY,relX));
    }

    public void draw(Graphics2D g){
        java.awt.geom.AffineTransform oldTransform = g.getTransform();
        
        g.rotate(Math.toRadians(angle+magicAngle),centerX,centerY);
        if((double)CurrentHealth/(double)MaxHealth>=0.8){
            rocket = rocket1;
        }
        else if((double)CurrentHealth/(double)MaxHealth>=0.4){
            rocket = rocket2;
        }
        else if((double)CurrentHealth/(double)MaxHealth<0.40){
            rocket = rocket3;
        }
        g.drawImage(rocket,(int)x,(int)y, null);
        g.setTransform(oldTransform);
    }

    public void move(){
        
        vX = velocity*Math.cos(Math.toRadians(angle));
        vY = velocity*Math.sin(Math.toRadians(angle));

        x += vX;
        y += vY;
        centerX = (int)x + (width/2);
        centerY = (int)y + (height/2);
    }

}
