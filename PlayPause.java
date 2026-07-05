package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PlayPause {
    int panelH;
    int panelW;
    int x;
    int y;
    int height = 260;
    int width = 260; 
    Image play;
    Image pause;
    Image currentImage;
    boolean paused;
    PlayPause(int panelH,int panelW,boolean paused){
        this.panelH = panelH;
        this.panelW = panelW;
        this.paused = paused;
        this.x = panelW/2 - width/2;
        this.y = panelH/2 - height/2;
        play = new ImageIcon(getClass().getResource("/SpaceGame/images/play.png")).getImage();
        pause = new ImageIcon(getClass().getResource("/SpaceGame/images/pause.png")).getImage();
    }

    public void updateBounds(int panelW, int panelH) {
        this.panelW = panelW;
        this.panelH = panelH;
        this.x = panelW/2 - width/2;
        this.y = panelH/2 - height/2;
    }

    public void draw(Graphics2D g){

        if(paused){
            currentImage = pause;
        }
        else{
            currentImage = play;
        }
        g.drawImage(currentImage, x, y,null);
    }

}
