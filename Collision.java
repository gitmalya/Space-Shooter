package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Collision {
    
    public static boolean AmmoEnemyCollision(Enemy enemy,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle evil = new Rectangle((int)enemy.x+10,(int)enemy.y+10,enemy.width-20,enemy.height-20);

        return bullet.intersects(evil);
    }

    public static boolean AmmoHealerCollision(HealDrop heal,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle healer = new Rectangle((int)heal.x+5,(int)heal.y+5,heal.width-10,heal.height-10);

        return bullet.intersects(healer);
    }

    public static boolean AmmoLifeStealCollision(LifeSteal life,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle steal = new Rectangle((int)life.x+5,(int)life.y+5,life.width-10,life.height-10);

        return bullet.intersects(steal);
    }

    public static boolean AmmoSuperShieldCollision(SuperShield shield,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle superShield = new Rectangle((int)shield.x+5,(int)shield.y+5,shield.width-10,shield.height-10);

        return bullet.intersects(superShield);
    }

    public static boolean AmmoFrostCollision(Frost frost,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle frosts = new Rectangle((int)frost.x+5,(int)frost.y+5,frost.width-10,frost.height-10);

        return bullet.intersects(frosts);
    }

    public static boolean AmmoBufferCollision(AmmoBuff buff,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle buffer = new Rectangle((int)buff.x+5,(int)buff.y+5,buff.width-10,buff.height-10);

        return bullet.intersects(buffer);
    }


    public static boolean PlayerEnemyCollision(Enemy enemy,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle evil = new Rectangle((int)enemy.x+20,(int)enemy.y+20,enemy.width-10,enemy.height-10);

        return ship.intersects(evil);
    }

    public static boolean PlayerHealerCollision(HealDrop healer,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle heal = new Rectangle((int)healer.x+5,(int)healer.y+5,healer.width-10,healer.height-10);

        return ship.intersects(heal);
    }

    public static boolean PlayerBufferCollision(AmmoBuff buff,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle buffer = new Rectangle((int)buff.x+5,(int)buff.y+5,buff.width-10,buff.height-10);

        return ship.intersects(buffer);
    }

    public static boolean PlayerLifeStealCollision(LifeSteal life,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle steal = new Rectangle((int)life.x+5,(int)life.y+5,life.width-10,life.height-10);

        return ship.intersects(steal);
    }


    public static boolean PlayerFrostCollision(Frost frost,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle frosts = new Rectangle((int)frost.x+5,(int)frost.y+5,frost.width-10,frost.height-10);

        return ship.intersects(frosts);
    }

    public static boolean PlayerSuperShieldCollision(SuperShield shield,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle superShield = new Rectangle((int)shield.x+5,(int)shield.y+5,shield.width-10,shield.height-10);

        return ship.intersects(superShield);
    }

    public static boolean PlayerBossCollision(BossForceField boss,ForceField field){
        Rectangle ship = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);
        Rectangle evilField = new Rectangle((int)boss.x,(int)boss.y,boss.radius*2,boss.radius*2);

        return ship.intersects(evilField);
    }

    public static boolean AmmoBossCollision(BossForceField field,Ammo ammo){
        Rectangle bullet = new Rectangle((int)ammo.x,(int)ammo.y,ammo.ammoRad*2,ammo.ammoRad*2);
        Rectangle boss = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);

        return bullet.intersects(boss);
    }

    public static boolean PlayerBossAmmoCollision(ForceField field,EvilAmmo evilAmmo){
        Rectangle bullet = new Rectangle((int)evilAmmo.x,(int)evilAmmo.y,evilAmmo.ammoRad*2,evilAmmo.ammoRad*2);
        Rectangle player = new Rectangle((int)field.x,(int)field.y,field.radius*2,field.radius*2);

        return bullet.intersects(player);
    }

}
