package SpaceGame;
import java.util.ArrayList;

public class BossSpawner {
    Player player;
    ArrayList<Boss> boss;
    ArrayList<BossForceField> bossField;
    BossSpawner(Player player,ArrayList<Boss> boss,ArrayList<BossForceField> bossField){
        this.player = player;
        this.boss = boss;
        this.bossField = bossField;
    }

    public void bossAdd(int randX,int magicHeight,double incBossHealth,double incBossSpeed,double incAmmoSpeed,double incAmmoDamage){
        Boss buss = new Boss(player, randX, magicHeight, incBossHealth, incBossSpeed,incAmmoSpeed,incAmmoDamage);
        boss.add(buss);
        bossField.add(new BossForceField(buss));
    }
    
}
