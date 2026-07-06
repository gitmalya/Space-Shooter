package SpaceGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
public class GamePanel extends JPanel implements ActionListener,MouseListener,MouseMotionListener,KeyListener{
    double panelH;
    double panelW;
    Timer timer;
    Timer spawner;
    Timer PlayNpause;
    Player player;
    boolean shooting;
    ArrayList<Ammo> ammo;
    ArrayList<Enemy> enemy;
    ForceField field;
    ArrayList<BossForceField> bossField;
    ArrayList<ParticleExplosion> explosion;
    ArrayList<LifeSteal>lifeSteal;
    ArrayList<Boss> boss;
    ArrayList<SuperShield> superShield;
    BossSpawner bossSpawner;
    PlayPause playPause;
    ArrayList<HealDrop> healDrop;
    ArrayList<AmmoBuff> ammoBuffs;
    int delay = 0;
    Image moving;
    Image notMoving;
    ActionListener spawn;
    Random rand;
    int ammoRemoveMaxHeight;
    int ammoRemoveMinHeight;
    int ammoRemoveMaxWidth;
    int ammoRemoveMinWidth;
    int powerUpRemoveMaxHeight;
    int ammoRange = 150;
    int magicHeight = -70;
    int minX;
    int maxX;
    int healTimer = 0;
    int healDelay = 840;
    int buffTimer = 0;
    int buffDelay = 1650;
    int lifeStealTimer = 0;
    int lifeStealDelay = 1380;
    int shieldTimer = 0;
    int shieldDelay = 1740;
    int killCount = 0;
    int difficultyNo;
    int difficultyCheck = 15;
    double newEnemyHealth = 0;
    double newEnemySpeed = 0;
    double IncrementSpeedNoForEnemy = 0.5;
    double IncrementHealthNoForEnemy = 1;
    double newBossHealth = 0;
    double newBossSpeed = 0;
    double IncrementHealthForBoss = 4;
    double IncrementSpeedForBoss = 0.25;
    double maxPlayerHealth;
    double newBossAmmoSpeed = 0;
    double newBossAmmoDamage = 0;
    double IncrementSpeedBossAmmo = 0.25;
    double IncrementDamageBossAmmo = 0.5;
    double MaxHealthBarWidth = 200;
    double healVelocity = 1;
    int MaxHealthBarHeight = 20;
    double healthBar;
    boolean keyReleased = false;
    boolean spaceClicked = false;
    boolean OneTime = true;
    boolean GameOver = false;
    boolean gamePause = false;
    boolean enterClicked = false;
    boolean uselessHit = false;
    Font font;
    Font font2;
    Image backGround;
    Image instructions;
    Image gameOver;
    Clip blastClip;
    Clip[] shootClip = new Clip[8];
    Clip gameOverClip;
    Clip healClip;
    Clip bossClip;
    Clip buffClip;
    String Level= "Difficulty: ";
    GamePanel(double panelH,double panelW){
        this.panelH = panelH;
        this.panelW = panelW;
        this.healthBar = MaxHealthBarWidth;
        this.difficultyNo = difficultyCheck;

        minX = 0;
        maxX = (int)panelW;

        ammoRemoveMaxHeight = (int)panelH+ammoRange;
        ammoRemoveMaxWidth = (int)panelW+ammoRange;
        ammoRemoveMinHeight = -ammoRange;
        ammoRemoveMinWidth = -ammoRange;
        powerUpRemoveMaxHeight = (int)panelH+ammoRange;

    

        loadAmmoSounds();
        player = new Player((int)panelW,(int)panelH);
        maxPlayerHealth = player.health;
        ammo = new ArrayList<>();
        enemy = new ArrayList<>();
        field = new ForceField(player);
        bossField = new ArrayList<>();
        explosion = new ArrayList<>();
        boss = new ArrayList<>();
        healDrop = new ArrayList<>();
        ammoBuffs = new ArrayList<>();
        playPause = new PlayPause((int)panelH, (int)panelW,gamePause);
        bossSpawner = new BossSpawner(player, boss,bossField);
        lifeSteal = new ArrayList<>();
        superShield = new ArrayList<>();
        moving = new ImageIcon(getClass().getResource("/SpaceGame/images/plane_speed.png")).getImage();
        notMoving = new ImageIcon(getClass().getResource("/SpaceGame/images/plane.png")).getImage();
        backGround = new ImageIcon(getClass().getResource("/SpaceGame/images/SpaceTheme2.png")).getImage();
        instructions = new ImageIcon(getClass().getResource("/SpaceGame/images/GameInstructions.png")).getImage();
        gameOver = new ImageIcon(getClass().getResource("/SpaceGame/images/GameOver.png")).getImage();
        setPreferredSize(new Dimension((int)panelW,(int)panelH));
        
        font = new Font("Arial",Font.BOLD,18);
        font2 = new Font("Arial",Font.BOLD,15);
        rand = new Random();
        timer = new Timer(16, this);
        timer.start();
        
       
        spawner = new Timer(1700,e -> spawnEnemy());
        spawner.start();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setRequestFocusEnabled(true);
        setOpaque(true);
    }

    public void updateGameSize() {
        panelW = getWidth();
        panelH = getHeight();
        player.updateBounds((int)panelW,(int)panelH);
        playPause.updateBounds((int)panelW,(int)panelH);

        maxX = (int)panelW;

        ammoRemoveMaxHeight = (int)panelH + ammoRange;
        ammoRemoveMaxWidth = (int)panelW + ammoRange;
        ammoRemoveMinHeight = -ammoRange;
        ammoRemoveMinWidth = -ammoRange;
        powerUpRemoveMaxHeight = (int)panelH+ammoRange;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        
        

        if(!spaceClicked){
            g.drawImage(instructions, 0, 0,getWidth(),getHeight(), null);
        }
        if(spaceClicked){
            g.drawImage(backGround,0,0,getWidth(),getHeight(), null);
            for(Ammo ammos : ammo){
            ammos.draw(g2d);
            }


            player.draw(g2d);
            
            field.draw(g2d);

            for(ParticleExplosion particle : explosion){
                particle.draw(g2d);
            }

            for(Enemy enemy : enemy){
                enemy.draw(g2d);
            }

            

            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString("Kill Count : "+killCount,getWidth()-150,25);
            

            difficultyState(g2d);

            for(Boss boss : boss){
                boss.draw(g2d);
                for(EvilAmmo evilAmmo : boss.evilAmmo){
                    evilAmmo.draw(g2d);
                }
            }

            for(BossForceField bossField : bossField){
                bossField.draw(g2d);
            }



            for(HealDrop heal : healDrop){
                heal.draw(g2d);
            }

            for(LifeSteal steal : lifeSteal){
                steal.draw(g2d);
            }

            for(SuperShield shield : superShield){
                shield.draw(g2d);
            }

            for(AmmoBuff buff : ammoBuffs){
                buff.draw(g2d);
            }


            if(GameOver){
                g.drawImage(gameOver, 0,getHeight()-570,getWidth(),260, null);
            }
            healthState(g2d);


            if(enterClicked){
                playPause.draw(g2d);
            }
            
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        updateGameSize();
        if(spaceClicked&&!GameOver){
            
            MethodMove();
            delayShoot();
            checkCollision();
            specialPowerUpsUpdate();
            dragEffect();
            HealSpawner();
            AmmoBuffSpawner();
            LifeStealSpawner();
            SuperShieldSpawner();
            bossLevel();
            increaseDifficulty();
            MaxHealing();
            HealthBar();
            repaint();
        }
        
    }


    public void specialPowerUpsUpdate(){
        player.updatePowerUp();
        field.updateForceField();
    }

    public void HealSpawner(){
        healTimer++;
        if((healTimer>=healDelay)){
            boolean spawning = rand.nextBoolean();
            healTimer = 0;
            if(spawning){
                healDrop.add(new HealDrop(maxX,minX, magicHeight,healVelocity));
            }
            
        }
    }

    public void AmmoBuffSpawner(){
        buffTimer++;
        if((buffTimer>=buffDelay)){
            boolean spawning = rand.nextBoolean();
            buffTimer = 0;
            if(spawning){
                ammoBuffs.add(new AmmoBuff(maxX,minX, magicHeight,1));
            }
            
        }

        if(!player.powerBuff){
            if(buffClip != null&&buffClip.isRunning()){
                buffClip.stop();
                buffClip.setFramePosition(0);
            }
        }
    }


    public void LifeStealSpawner(){
        lifeStealTimer++;
        if((lifeStealTimer>=lifeStealDelay)){
            boolean spawning = rand.nextBoolean();
            lifeStealTimer = 0;
            if(spawning){
                lifeSteal.add(new LifeSteal(maxX,minX, magicHeight,2));
            }
            
        }
    }


    public void SuperShieldSpawner(){
        shieldTimer++;
        if((shieldTimer>=shieldDelay)){
            boolean spawning = rand.nextBoolean();
            shieldTimer = 0;
            if(spawning){
                superShield.add(new SuperShield(maxX,minX, magicHeight,2));
            }
            
        }
    }


    public void increaseDifficulty(){
        if(killCount>=difficultyCheck){
            newEnemyHealth += IncrementHealthNoForEnemy;
            newEnemySpeed += IncrementSpeedNoForEnemy;
            newBossHealth += IncrementHealthForBoss;
            newBossSpeed += IncrementSpeedForBoss;
            newBossAmmoSpeed += IncrementSpeedBossAmmo;
            newBossAmmoDamage += IncrementDamageBossAmmo;
            difficultyCheck += difficultyNo;
        }
    }



    public void HealthBar(){
        healthBar = (double)MaxHealthBarWidth*(((double)player.health)/(double)maxPlayerHealth);
    }

    public void MethodMove(){
        if(ammo.size()>=120){
            ammo.remove(0);
        }
        for(int i = 0;i<ammo.size();i++){
            ammo.get(i).move(); 
            if(ammo.get(i).y<ammoRemoveMinHeight||ammo.get(i).x<ammoRemoveMinWidth||ammo.get(i).y>ammoRemoveMaxHeight||ammo.get(i).x>ammoRemoveMaxWidth){
                ammo.remove(i);
                i--;
            }
        }

        
            player.move();


        for(int i=0;i<explosion.size();i++){
            explosion.get(i).move();
            if(explosion.get(i).end()){
                explosion.remove(i);
                i --;
            }
        }


        for(Enemy enemy : enemy){
            enemy.move();
            enemy.UpdateAngle();
        }


        for(int i =0;i<boss.size();i++){
            boss.get(i).move();
            boss.get(i).UpdateAngle();
            boss.get(i).ammoShoot();
            for(int j=0;j<boss.get(i).evilAmmo.size();j++){
                boss.get(i).evilAmmo.get(j).move();
                if(boss.get(i).evilAmmo.get(j).y<ammoRemoveMinHeight||boss.get(i).evilAmmo.get(j).x<ammoRemoveMinWidth||boss.get(i).evilAmmo.get(j).y>ammoRemoveMaxHeight||boss.get(i).evilAmmo.get(j).x>ammoRemoveMaxWidth){
                    boss.get(i).evilAmmo.remove(j);
                    j--;
                }
            }
        }

        for(int i=0;i<healDrop.size();i++){
                healDrop.get(i).move();
                if(healDrop.get(i).y>powerUpRemoveMaxHeight){
                    healDrop.remove(i);
                }
        }

        for(int i=0;i<lifeSteal.size();i++){
                lifeSteal.get(i).move();
                if(lifeSteal.get(i).y>powerUpRemoveMaxHeight){
                    lifeSteal.remove(i);
                }
        }

        for(int i=0;i<superShield.size();i++){
                superShield.get(i).move();
                if(superShield.get(i).y>powerUpRemoveMaxHeight){
                    superShield.remove(i);
                }
        }

        for(int i=0;i<ammoBuffs.size();i++){
                ammoBuffs.get(i).move();
                if(ammoBuffs.get(i).y>powerUpRemoveMaxHeight){
                    ammoBuffs.remove(i);
                }
        }

        for(BossForceField bossField : bossField){
            bossField.move();
        }

    }

    public void delayShoot(){
        if(delay>0){
            delay--;
        }
        else if (shooting&&delay==0) {
            ammo.add(new Ammo(player));
            beamSound();
            delay = 5;
        }
    }

    public void bossLevel(){
        if(killCount>=difficultyCheck){
            int randX = rand.nextInt((maxX - minX)+1)+minX;
            bossSpawner.bossAdd(randX,magicHeight,newBossHealth,newBossSpeed,newBossAmmoSpeed,newBossAmmoDamage);
            bossSound();
        }
    }

    public void checkCollision(){


        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<enemy.size();j++){
                if(Collision.AmmoEnemyCollision(enemy.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoEnemyDamage(i, j);
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }


        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<healDrop.size();j++){
                if(Collision.AmmoHealerCollision(healDrop.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoHealerDamage(i, j);
                    HealthBar();
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }

        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<ammoBuffs.size();j++){
                if(Collision.AmmoBufferCollision(ammoBuffs.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoBufferDamage(i, j);
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }

        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<lifeSteal.size();j++){
                if(Collision.AmmoLifeStealCollision(lifeSteal.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoLifeStealDamage(i, j);
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }

        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<superShield.size();j++){
                if(Collision.AmmoSuperShieldCollision(superShield.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoSuperShieldDamage(i, j);
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }

        for(int i=0;i<enemy.size();i++){
            if(Collision.PlayerEnemyCollision(enemy.get(i),field)){
                EnemyDamage(i);
                HealthBar();
                blastSound();
                startExplosion(enemy.get(i).x+enemy.get(i).width/2,enemy.get(i).y+enemy.get(i).height/2);
                enemy.remove(i);
                i--;
                killCount ++;
                break;
            }
        }


        for(int i=0;i<healDrop.size();i++){
            if(Collision.PlayerHealerCollision(healDrop.get(i),field)){
                player.health += healDrop.get(i).healPoints;
                MaxHealing();
                HealthBar();
                healSound();
                healDrop.remove(i);
                i--;
                break;
            }
        }

        for(int i=0;i<ammoBuffs.size();i++){
            if(Collision.PlayerBufferCollision(ammoBuffs.get(i),field)){
                player.powerBuffStart();
                buffSound();
                ammoBuffs.remove(i);
                i--;
                break;
            }
        }

        for(int i=0;i<lifeSteal.size();i++){
            if(Collision.PlayerLifeStealCollision(lifeSteal.get(i),field)){
                player.lifeStealStart();
                lifeSteal.remove(i);
                i--;
                break;
            }
        }

        for(int i=0;i<superShield.size();i++){
            if(Collision.PlayerSuperShieldCollision(superShield.get(i),field)){
                player.superShieldStart();
                superShield.remove(i);
                i--;
                break;
            }
        }


        for(int i=0;i<bossField.size();i++){
            if(Collision.PlayerBossCollision(bossField.get(i),field)){
                blastSound();
                BossDamage(i);
                bossField.remove(i);
                startExplosion(boss.get(i).x+boss.get(i).width/2,boss.get(i).y+boss.get(i).height/2);
                boss.remove(i);
                blastSound();
                HealthBar();
                i--;
                killCount ++;
                break;
            }
        }

        for(int i=0;i<ammo.size();i++){
            for(int j=0;j<bossField.size();j++){
                if(Collision.AmmoBossCollision(bossField.get(j),ammo.get(i))){
                    LifeStealing(i);
                    AmmoBossDamage(i, j);
                    ammo.remove(i);
                    i--;
                    break;
                }
            }
        }

        for(int i=0;i<boss.size();i++){
            for(int j=0;j<boss.get(i).evilAmmo.size();j++){
                if(Collision.PlayerBossAmmoCollision(field,boss.get(i).evilAmmo.get(j))){
                    BossAmmoDamage(i,j);
                    HealthBar();
                    boss.get(i).evilAmmo.remove(j);
                    j--;
                    break;
                }
            }
        }
    }


    public void AmmoHealerDamage(int i,int j){
        healDrop.get(j).healerHealth -= ammo.get(i).damage;
        if(healDrop.get(j).healerHealth<=0){
            player.health += healDrop.get(j).healPoints;
            MaxHealing();
            healSound();
            healDrop.remove(j);
        } 
    }

    public void AmmoBufferDamage(int i,int j){
        ammoBuffs.get(j).health -= ammo.get(i).damage;
        if(ammoBuffs.get(j).health<=0){
            player.powerBuffStart();
            buffSound();
            ammoBuffs.remove(j);
        } 
    }

    public void AmmoLifeStealDamage(int i,int j){
        lifeSteal.get(j).health -= ammo.get(i).damage;
        if(lifeSteal.get(j).health<=0){
            player.lifeStealStart();
            lifeSteal.remove(j);
        } 
    }

    public void AmmoSuperShieldDamage(int i,int j){
        superShield.get(j).health -= ammo.get(i).damage;
        if(superShield.get(j).health<=0){
            player.superShieldStart();
            superShield.remove(j);
        } 
    }

    public void LifeStealing(int i){
        if(ammo.get(i).healing>0){
            player.health += ammo.get(i).healing;
            MaxHealing();
        }
            
        
    }

    public void MaxHealing(){
        if(player.health>=player.maxPlayerHealth){
            player.health = player.maxPlayerHealth;
        }
    }

    public void dragEffect(){
        if((player.vX)!=0&&keyReleased){
            if(player.vX>0){
                player.vX -= player.drag;
                if(player.vX<0){
                    player.vX = 0;
                }
            }
            else if(player.vX<0){
                player.vX -= (-1)*player.drag;
                if(player.vX>0){
                    player.vX = 0;
                }
            }
                
        }
        if((player.vY)!=0&&keyReleased){
            if(player.vY>0){
                player.vY -= player.drag;
                if(player.vY<0){
                    player.vY = 0;
                }
            }
            else if(player.vY<0){
                player.vY -= (-1)*player.drag;
                if(player.vY>0){
                    player.vY = 0;
                }
            }
        }
    }

    public boolean Shield(){
        return player.superShield;
    }

    public void beamSound(){
        for (Clip clip : shootClip){
        if (!clip.isRunning()&&clip != null){
            clip.setFramePosition(0);
            clip.start();
            break;
        }
    }
    }


    public void healSound(){
        try{ AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/heal.wav"));
            healClip = AudioSystem.getClip();
            healClip.open(audio);
            healClip.start();
        }
       catch(Exception e){
            e.printStackTrace();
        }
    }


    public void loadAmmoSounds(){
        try{
            for (int i = 0; i < shootClip.length; i++) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/laser1.wav"));

            shootClip[i] = AudioSystem.getClip();
            shootClip[i].open(audio);
            audio.close();
            }   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bossSound(){
        try{ AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/boss1.wav"));
            bossClip = AudioSystem.getClip();
            bossClip.open(audio);
            bossClip.start();
        }
       catch(Exception e){
            e.printStackTrace();
        }
    }

    public void buffSound(){
        try{ AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/powerups2.wav"));
            buffClip = AudioSystem.getClip();
            buffClip.open(audio);
            buffClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
       catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void blastSound(){

        try{ AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/blast.wav"));
            blastClip = AudioSystem.getClip();
            blastClip.open(audio);
            blastClip.start();
        }
       catch(Exception e){
            e.printStackTrace();
        }
    }

    public void gameOverSound(){

        try{ AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/SpaceGame/sfx/gameover.wav"));
            gameOverClip = AudioSystem.getClip();
            gameOverClip.open(audio);
            gameOverClip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }



    public void AmmoEnemyDamage(int i,int j){
        enemy.get(j).CurrentHealth -= ammo.get(i).damage;
        if(enemy.get(j).CurrentHealth<=0){
            startExplosion(enemy.get(j).x+enemy.get(j).width/2,enemy.get(j).y+enemy.get(j).height/2);
            enemy.remove(j);
            blastSound();
            killCount ++;
        }
    }

    public void AmmoBossDamage(int i,int j){
        boss.get(j).CurrentHealth -= ammo.get(i).damage;
        if(boss.get(j).CurrentHealth<=0){
            bossField.remove(j);
            startExplosion(boss.get(j).x+boss.get(j).width/2,boss.get(j).y+boss.get(j).height/2);
            boss.remove(j);
            blastSound();
            killCount ++;
        }
    }

    public void BossAmmoDamage(int i,int j){
        if(Shield()){
            return;
        }
        player.health -= boss.get(i).evilAmmo.get(j).damage;
        CheckGameOver();
    }

    public void EnemyDamage(int i){
        if(Shield()){
            return;
        }
        player.health -= enemy.get(i).damage;
        CheckGameOver();
    }

    public void BossDamage(int i){
        if(Shield()){
            return;
        }
        player.health -= boss.get(i).damage;
        CheckGameOver();
    }

    public void CheckGameOver(){
        if(player.health<=0){
            player.health = 0;
            GameOver = true;
            stopSound();
            gameOverSound();
            timer.stop();
            spawner.stop();
        }
    }

    public void stopSound(){
        if(buffClip != null){
            buffClip.stop();
            buffClip.setFramePosition(0);
        }
        if(bossClip != null){
            bossClip.stop();
            bossClip.setFramePosition(0);
        }
        if(shootClip != null){
            for(int i=0;i<shootClip.length;i++){
                shootClip[i].stop();
                shootClip[i].setFramePosition(0);
            }
            
        }
        if(blastClip != null){
            blastClip.stop();
            blastClip.setFramePosition(0);
        }
        if(gameOverClip != null){
            gameOverClip.stop();
            gameOverClip.setFramePosition(0);
        }
        if(healClip != null){
            healClip.stop();
            healClip.setFramePosition(0);
        }
    }

    public void startExplosion(double x , double y){
        for(int i=1;i<=300;i++){
            explosion.add(new ParticleExplosion((int)x,(int) y));
        }
    }



    public void difficultyState(Graphics2D g){
        g.setFont(font2);
        
        if(killCount/difficultyNo<1){
            g.setColor(new Color(49, 204, 118));
            g.drawString(Level+"Very Easy",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<2){
            g.setColor(new Color(0, 255, 60));
            g.drawString(Level+"Easy",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<3){
            g.setColor(new Color(242, 255, 0));
            g.drawString(Level+"Medium",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<4){
            g.setColor(new Color(235, 150, 5));
            g.drawString(Level+"Hard",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<5){
            g.setColor(new Color(255, 97, 5));
            g.drawString(Level+"Extreme",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<6){
            g.setColor(new Color(255, 47, 5));
            g.drawString(Level+"Insane",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<7){
            g.setColor(new Color(255, 5, 5));
            g.drawString(Level+"Insane +",getWidth()-150,45);
        }
        else if(killCount/difficultyNo<8){
            g.setColor(Color.RED);
            g.drawString(Level+"Insane ++",getWidth()-150,45);
        }
        g.setFont(font);
    }

    public void healthState(Graphics2D g){
        Stroke oldstroke = g.getStroke();
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(4));
        g.drawRect(10, 12,(int)MaxHealthBarWidth,MaxHealthBarHeight);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(10, 12,(int)MaxHealthBarWidth,MaxHealthBarHeight);
        g.setStroke(oldstroke);

        if((((double)player.health)/maxPlayerHealth)>=0.90){
            g.setColor(Color.green);
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.80){
            g.setColor(new Color(77, 255, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.70){
            g.setColor(new Color(11, 255, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.60){
            g.setColor(new Color(140, 255, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.50){
            g.setColor(new Color(212, 255, 0)); 
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.40){
            g.setColor(new Color(255, 242, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.30){
            g.setColor(new Color(255, 140, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0.20){
            g.setColor(new Color(255, 42, 0));
        }
        else if((((double)player.health)/maxPlayerHealth)>=0){
            g.setColor(Color.RED);
        }
       
        g.fillRect(10, 12,(int)healthBar,MaxHealthBarHeight);
    }


    
    @Override
    public void mousePressed(MouseEvent e) {
       shooting = true;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
       shooting = false;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseDragged(MouseEvent e) {
       player.updateAngle(e.getX(),e.getY());
    }


    @Override
    public void mouseMoved(MouseEvent e) {
       player.updateAngle(e.getX(),e.getY());
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if(OneTime&&!GameOver){
            if(e.getKeyChar()==KeyEvent.VK_SPACE){
                spaceClicked = true;
                OneTime = false;
            }
        }
        else if(GameOver&&!OneTime){
            if(e.getKeyChar()==KeyEvent.VK_SPACE){
                Restart(); 
            }
        }
        

        if(spaceClicked){
            if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_S){
                player.spaceShip = moving;
                keyReleased = false;
                if(e.getKeyCode()==KeyEvent.VK_A){
                    player.vX = -5;
                }
                else if(e.getKeyCode()==KeyEvent.VK_D){
                    player.vX = 5;
                }
                if(e.getKeyCode()==KeyEvent.VK_W){
                    player.vY = -5;
                }
                else if(e.getKeyCode()==KeyEvent.VK_S){
                    player.vY = 5;
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(!GameOver&&!uselessHit){
                    gamePause = !(gamePause); 
                    enterClicked = true;
                    PlayPause();

                    
                    
                    
                }
                
            }
        }

        
        

    }


    @Override
    public void keyReleased(KeyEvent e) {
        if(spaceClicked){
            player.spaceShip = notMoving;
            if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_D){
                keyReleased = true;
                
            }
            else if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_S){
                keyReleased = true;
            }
            
        }
        

    }

    public void spawnEnemy(){
        if(spaceClicked){
            int attempts = 0;
            boolean spawnX;
            int randX = rand.nextInt((maxX - minX)+1)+minX;

            do{
                spawnX = true;
                for(Enemy e : enemy){
                    if(Math.abs(randX-e.x)<70){
                        spawnX = false;
                        break;
                    }
                }
                attempts++;
            }while(!spawnX&&attempts<25);

            if(spawnX){
                enemy.add(new Enemy(player,randX,magicHeight,newEnemyHealth,newEnemySpeed));
            }
        }
        
    }


    public void Restart(){
        stopSound();
        ammo.clear();
        boss.clear();
        enemy.clear();
        healDrop.clear();
        bossField.clear();
        ammoBuffs.clear();
        lifeSteal.clear();
        superShield.clear();
        newBossAmmoDamage = 0;
        newBossAmmoSpeed = 0;
        newBossHealth = 0;
        newBossSpeed = 0;
        newEnemyHealth = 0;
        newEnemySpeed = 0;
        delay = 0;
        healTimer = 0;
        buffTimer = 0;
        lifeStealTimer = 0;
        shieldTimer = 0;
        difficultyCheck = difficultyNo;
        killCount = 0;
        healthBar = MaxHealthBarWidth;
        loadAmmoSounds();
        player = new Player((int)panelW,(int)panelH);
        ammo = new ArrayList<>();
        enemy = new ArrayList<>();
        field = new ForceField(player);
        bossField = new ArrayList<>();
        boss = new ArrayList<>();
        explosion = new ArrayList<>();
        healDrop = new ArrayList<>();
        lifeSteal = new ArrayList<>();
        playPause = new PlayPause((int)panelH, (int)panelW,gamePause);
        bossSpawner = new BossSpawner(player, boss,bossField);
        ammoBuffs = new ArrayList<>();
        superShield = new ArrayList<>();
        GameOver = false;
        gamePause = false;
        keyReleased = false;
        enterClicked = false;
        uselessHit = false;
        timer.start();
        spawner.start();
        repaint();
    }


    public void PlayPause(){
        
        playPause.paused = gamePause;
        
        if(gamePause){
            
            stopSound();
            repaint();
            timer.stop();
            spawner.stop();
        }
        else{
            repaint();
            uselessHit = true;
            PlayNpause = new Timer(500, e ->{
                enterClicked = false;
                timer.start();
                spawner.start();
                uselessHit = false;
            });
            PlayNpause.setRepeats(false);
            PlayNpause.start();
            
        }
    }



       
}

