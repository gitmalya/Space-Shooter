package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class EvilAmmo {
    double x;
    double y;
    int magicNo = 58;
    Boss boss;
    double velocity = 7;
    double damage = 1;
    double incAmmoDamage = 0;
    double incAmmoSpeed = 0;
    int ammoRad = 4;
    double angle;
    double healthLevel;
    EvilAmmo(Boss boss){
        this.boss = boss;
        this.angle = boss.angle;
        healthLevel = ((double)boss.CurrentHealth/(double)boss.MaxHealth);
        
        if(healthLevel>=0.75){
            ammoRad++;
        }
        else if(healthLevel>=0.50){
            ammoRad += 2;
        }
        else if(healthLevel>=0.20){
            ammoRad += 3;
        }
        else if(healthLevel>=0){
            ammoRad += 4;
        }
        this.x = boss.centerX+magicNo*Math.cos(Math.toRadians(angle))-ammoRad;
        this.y = boss.centerY+magicNo*Math.sin(Math.toRadians(angle))-ammoRad;
        this.incAmmoSpeed = boss.incAmmoSpeed;
        this.incAmmoDamage = boss.incAmmoDamage;
        this.velocity += this.incAmmoSpeed;
        this.damage += this.incAmmoDamage;
        
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.CYAN);
        g.fillOval((int)x,(int) y, ammoRad*2, ammoRad*2);
    }

    public void move(){
        x += velocity*Math.cos(Math.toRadians(angle));
        y += velocity*Math.sin(Math.toRadians(angle));
    }
}

