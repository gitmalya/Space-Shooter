package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BossForceField {
    Boss boss;
    int stroke = 3;
    int radius = 50;
    int x;
    int y;
    double healthLevel;
    BossForceField(Boss boss){
        this.boss = boss;
        this.x = (int)(boss.centerX-radius);
        this.y = (int)(boss.centerY-radius);
    }

    public void draw(Graphics2D g){
        Stroke oldstroke = g.getStroke();
        g.setStroke(new BasicStroke(stroke));
        if(healthLevel>=0.90){
            g.setColor(Color.green);
        }
        else if(healthLevel>=0.80){
            g.setColor(new Color(77, 255, 0));
        }
        else if(healthLevel>=0.70){
            g.setColor(new Color(11, 255, 0));
        }
        else if(healthLevel>=0.60){
            g.setColor(new Color(140, 255, 0));
        }
        else if(healthLevel>=0.50){
            g.setColor(new Color(212, 255, 0));
        }
        else if(healthLevel>=0.40){
            g.setColor(new Color(255, 242, 0));
        }
        else if(healthLevel>=0.30){
            g.setColor(new Color(255, 140, 0));
        }
        else if(healthLevel>=0.20){
            g.setColor(new Color(255, 42, 0));
        }
        else if(healthLevel>=0){
            g.setColor(Color.RED);
        }
        g.drawOval(x,y,radius*2, radius*2);
        g.setStroke(oldstroke);
    }

    public void move(){
        healthLevel = ((double)boss.CurrentHealth/(double)boss.MaxHealth);
        x = (int)(boss.centerX-radius);
        y = (int)(boss.centerY-radius);
    }

}
