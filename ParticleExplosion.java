package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ParticleExplosion {
    double x;
    double y;
    double speed;
    int MaxSize = 6;
    int MinSize = 2;
    double MaxSpeed = 6;
    double MinSpeed = 0.5;
    int size;
    double angle;
    double dx;
    double dy;
    int span;
    Color[] colors = {Color.RED,Color.CYAN,Color.GREEN,Color.ORANGE,Color.BLUE,Color.WHITE,Color.YELLOW};
    Color color;
    ParticleExplosion(int x,int y){
        this.x = x;
        this.y = y;

        span = 30;

        size = (int)(Math.random()*(MaxSize - 1))+MinSize;
        angle = Math.random()*Math.PI*2;
        speed = Math.random()*(MaxSpeed - 1)+MinSpeed;

        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;

        color = colors[(int)(Math.random() * colors.length)];
    }

    public void draw(Graphics2D g){
        Composite old = g.getComposite();
        float opacity;
        if(span>10){
            opacity = 1.0f;
        }
        else{
            opacity = span/10f;
        }

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity));

        g.setColor(color);
        g.fillOval((int)x,(int) y, size, size);
        g.setComposite(old);
    }

    public void move(){
        x += dx;
        y += dy;
        dx *= 0.95;
        dy *= 0.95;
        span --;
    }

    public boolean end(){
        return (span <= 0);
    }

}
