package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Ammo {
    double x;
    double y;
    int magicNo = 60;
    Player player;
    double velocity;
    double damage;
    double healing;
    int ammoRad;
    double angle;
    boolean powerBuff;
    boolean lifeSteal;
    Ammo(Player player){
        this.player = player;
        this.angle = player.angle;
        this.ammoRad = player.currentAmmoRad;
        this.damage = player.currentAmmoDamage;
        this.velocity = player.currentAmmoSpeed;
        this.healing = player.playerAmmoHeal;
        this.powerBuff = player.powerBuff;
        this.lifeSteal = player.lifeStealBuff;
        this.x = player.centerX+magicNo*Math.cos(Math.toRadians(angle))-ammoRad;
        this.y = player.centerY+magicNo*Math.sin(Math.toRadians(angle))-ammoRad;
        
    }
    
    public void draw(Graphics2D g){
        if(powerBuff&&!lifeSteal){
            g.setPaint(new GradientPaint((float)x,(float)y, Color.ORANGE,(float)(x+ammoRad*2),(float)(y+ammoRad*2),Color.RED));
        }
        else if(powerBuff&&lifeSteal){
            g.setPaint(new GradientPaint((float)x,(float)y, Color.RED,(float)(x+ammoRad*2),(float)(y+ammoRad*2),Color.GREEN));
        }
        else if(!powerBuff&&lifeSteal){
            g.setColor(Color.GREEN);
        }
        else{
            g.setColor(Color.WHITE);
        }
        
        g.fillOval((int)x,(int) y, ammoRad*2, ammoRad*2);
    }

    public void move(){
        x += velocity*Math.cos(Math.toRadians(angle));
        y += velocity*Math.sin(Math.toRadians(angle));
    }
}
