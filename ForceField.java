package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ForceField {
    Player player;
    int stroke = 3;
    int radius;
    int x;
    int y;
    ForceField(Player player){
        this.player = player;
        this.radius = player.playerForceFieldRadius;
        this.x = player.centerX-radius;
        this.y = player.centerY-radius;
        
    }

    public void updateForceField(){
        if(player.superShield){
            radius = (int)((double)player.playerForceFieldRadius * 1.7);
        }
        else{
            radius = player.playerForceFieldRadius;
        }

        x = player.centerX-radius;
        y = player.centerY-radius;
    }

    public void draw(Graphics2D g){
        Stroke oldstroke = g.getStroke();
        g.setStroke(new BasicStroke(stroke));

        if(player.powerBuff&&!player.superShield){
            g.setPaint(new GradientPaint((float)x,(float)y, Color.WHITE,(float)(x+radius*2),(float)(y+radius*2),Color.RED));
        }
        else if(player.superShield&&!player.powerBuff){
            g.setPaint(new GradientPaint((float)x,(float)y, Color.WHITE,(float)(x+radius*2),(float)(y+radius*2),Color.BLUE));
        }
        else if(player.superShield&&player.powerBuff){
            g.setPaint(new GradientPaint((float)x,(float)y, Color.RED,(float)(x+radius*2),(float)(y+radius*2),Color.BLUE));
        }
        else{
            g.setColor(Color.CYAN);
        }

       
        
        g.drawOval(x,y,radius*2, radius*2);
        g.setStroke(oldstroke);
    }


}
