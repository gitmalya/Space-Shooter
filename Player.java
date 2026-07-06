package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Player {
    Image spaceShip;
    double angle = 0;
    int x;
    int y;
    int width = 64;
    int height = 64;
    double vX = 0;
    double vY = 0;
    int magicNo = 20;
    int magicAngle = 45;
    int centerX;
    int centerY;
    int panelH;
    int panelW;
    double drag = 0.20;
    double maxPlayerHealth = 30;
    double health;
    int playerAmmoRad = 4;
    int currentAmmoRad;
    int playerAmmoDamage = 1;
    double playerAmmoHeal = 0.20;
    int currentAmmoDamage;
    int playerAmmoSpeed = 10;
    int currentAmmoSpeed;
    int playerForceFieldRadius = 50;
    boolean lifeStealBuff = false;
    boolean powerBuff = false;
    boolean superShield = false;
    int buffTime;
    int buffDuration = 300;
    int lifeStealTime;
    int lifeStealDuration = 330;
    int shieldTime;
    int shieldDuration = 120;
    Player(int panelW,int panelH){
        this.panelH = panelH;
        this.panelW = panelW;
        this.x = panelW/2 - width;
        this.y = panelH/2 + 4*height;
        this.health = maxPlayerHealth;
        this.currentAmmoRad = playerAmmoRad;
        this.currentAmmoDamage = playerAmmoDamage;
        this.currentAmmoSpeed = playerAmmoSpeed;
        centerX = x + (width/2);
        centerY = y + (height/2);
        spaceShip = new ImageIcon(getClass().getResource("/SpaceGame/images/plane.png")).getImage();
    }

    public void updateAngle(int mouseX, int mouseY){
        double delX = mouseX-centerX;
        double delY = mouseY-centerY;

        angle = Math.toDegrees(Math.atan2(delY, delX));
    }

    public void powerBuffStart(){
        powerBuff = true;
        buffTime = 0;
        currentAmmoRad = playerAmmoRad*3;
        currentAmmoDamage = playerAmmoDamage*2;
        currentAmmoSpeed = playerAmmoSpeed +2;
    }

    public void lifeStealStart(){
        lifeStealBuff = true;
        lifeStealTime = 0;
    }

    public void superShieldStart(){
        superShield = true;
        shieldTime = 0;
    }

    public void updatePowerUp() {
        if (powerBuff) {

            buffTime++;

            if(buffTime >= buffDuration){
                powerBuff = false;
                currentAmmoRad = playerAmmoRad;
                currentAmmoDamage = playerAmmoDamage;
                currentAmmoSpeed = playerAmmoSpeed;
                buffTime = 0;
            }

        }

        if(lifeStealBuff){
            lifeStealTime++;
            if(lifeStealTime >= lifeStealDuration){
                lifeStealBuff = false;
                lifeStealTime = 0;
            }
        }

        if(superShield){
            shieldTime ++;
            if(shieldTime >= shieldDuration){
                superShield = false;
                shieldTime = 0;
            }
        }
    }

    public void draw(Graphics2D g){
        java.awt.geom.AffineTransform oldTransform = g.getTransform();
        g.rotate(Math.toRadians(angle+magicAngle),centerX,centerY);
        g.drawImage(spaceShip,x,y, null);
        

        g.setTransform(oldTransform);
    }

    public void updateBounds(int panelW, int panelH) {
        this.panelW = panelW;
        this.panelH = panelH;
    }

    

    public void move(){
        x+=vX;
        y+=vY;
        if(x<magicNo){
            x = magicNo; 
        }
        if(x>panelW-width-magicNo){
            x = panelW-width-magicNo;
        }
        if(y<magicNo){
            y = magicNo;
        }
        if(y>panelH-height-magicNo){
            y=panelH-height-magicNo;
        }

        centerX = x + (width/2);
        centerY = y + (height/2);
            
    }
}
