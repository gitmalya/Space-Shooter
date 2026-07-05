package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
    
public class Boss {
    double x;
    double y;
    double velocity = 0.5;
    double vX;
    double vY;
    Player player;
    double relX;
    double relY;
    double angle;
    int centerX;
    int centerY;
    int shootTimer = 0;
    int shootDelay = 60;
    double MaxHealth = 12;
    double CurrentHealth;
    double incAmmoDamage;
    double incAmmoSpeed;
    int width = 64;
    int height = 64;
    int magicAngle = 45;
    int damage = 30;
    Image boss;
    ArrayList<EvilAmmo> evilAmmo;
    Boss(Player player,double x,double y,double incHealth,double incSpeed,double incAmmoSpeed,double incAmmoDamage){
        this.player = player;
        this.x = x;
        this.y = y;
        this.MaxHealth += incHealth;
        this.CurrentHealth = MaxHealth;
        this.velocity += incSpeed;
        this.evilAmmo = new ArrayList<>();
        this.incAmmoSpeed = incAmmoSpeed;
        this.incAmmoDamage = incAmmoDamage;


        boss = new ImageIcon(getClass().getResource("/SpaceGame/images/boss.png")).getImage();
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
        g.drawImage(boss,(int)x,(int)y, null);
        g.setTransform(oldTransform);
    }

    public void ammoCreate(){
        evilAmmo.add(new EvilAmmo(this));
    }


    public void ammoShoot(){
        shootTimer++;
        if(shootTimer>=shootDelay){
            ammoCreate();
            shootTimer = 0;
        }
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
