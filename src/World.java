import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class World extends JPanel{

    //JPanel是Java图形用户界面工具包swing中的面板容器类
    //包含在Javax.swing包中
    //是一个轻量级的容器，可以加入到JFrame窗体中（相框）


    //声明一个窗口的宽度和高度
    //声明一个窗口的宽度和高度
    public static final int WIDTH=450;//窗口宽度
    public static final int HEIGHT=650;//窗口的高度
    private Sky sky =new Sky();
    private FlyingObject[] enemies={};//将生成敌人放入数组
    private Hero hero=new Hero();
    private Bullet[] bullets={};//子弹数组

    private Boss boss=new Boss();
    private BossBullet[] left={};
    private BossBullet[] right={};
    private BossBullet[] mid={};
    private int timeIndex=1;
    private int time=0;
    private int score;//分数
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameOver;
    public static BufferedImage victory;
    public static final int START=0;//启动状态
    public static final int PAUSE=1;//暂停状态
    public static final int RUNNING=2;//运行状态
    public static final int GAME_OVER=3;//游戏结束状态.
    public static final int VICTORY=4;
    public int state=START;//默认为活着状态
    public int new_score;
    static{//初始化
        start=FlyingObject.loadImage("start.png");
        pause=FlyingObject.loadImage("pause1.png");
        gameOver=FlyingObject.loadImage("gameover.png");
        victory=FlyingObject.loadImage("victory.png");
    }

    //生成敌人

    private FlyingObject nextOne(){
        //return new Boss();
        Random random=new Random();
        int n=random.nextInt(21);
        if(n<8){
            return new Airplane();
        }else if(n<14){
            return new BigAirplane();
        }else
            return new Helicopter();
    }

    //敌人入场
    private int enemiesIndex=0;
    public void enemiesAction(){
        enemiesIndex++;
        //将刷新时间扩展30倍   10*30毫秒
        if(enemiesIndex%40==0){
            enemies=Arrays.copyOf(enemies, enemies.length +1);
            //生成boss

            //生成对象
            FlyingObject f=nextOne();

            //把随机生成的对象放在数组中的最后一个元素
            enemies[enemies.length-1]=f;

        }
    }



    /*子弹入场*/
    private int bulletIndex=0;
    private void BulletAction(){
        bulletIndex++;
        if(bulletIndex%20==0){
            Bullet[] b=hero.shoot();//生成子弹
            bullets=Arrays.copyOf(bullets,bullets.length+ b.length);//数组扩容
            //数组的复制（把生成的子弹装进新的数组Enemies中)
            System.arraycopy(b, 0, bullets, bullets.length-b.length, b.length);
        }
        //	System.out.println("子弹移动了："+bullets.length);
    }

    /*boss左子弹入场*/
    int BossLeftIndex=0;
    public void BossLeftBulletAction(){
        if(BossLeftIndex++%100==0){
            BossBullet leftB=boss.bossLeftShoot();
            left=Arrays.copyOf(left, left.length+1);
            left[left.length-1]=leftB;
        }

    }


    /*Boss中间子弹入场*/
    int BossMidIndex=0;
    public void BossMidBulletAction(){
        if(BossMidIndex++%100==0){
            BossBullet midB=boss.bossMidShoot();
            mid=Arrays.copyOf(mid,mid.length+1);
            mid[mid.length-1]=midB;
        }
    }

    /*Boss右边子弹入场*/
    int BossRightIndex=0;
    public void BossRightBulletAction(){
        if(BossRightIndex++%100==0){
            BossBullet rightB=boss.bossRightShoot();
            right=Arrays.copyOf(right, right.length+1);
            right[right.length-1]=rightB;
        }
    }


    /*子弹和敌人相撞*/
    public void bulletBangAction(){
        for(int i=0;i<bullets.length;i++){//遍历所有子弹
            Bullet b=bullets[i];
            for(int j=0;j<enemies.length;j++){//遍历所有敌人
                FlyingObject f=enemies[j];
                if(b.isLife()&&f.isLife()&&f.hit(b)){//判断每个敌机是否与子弹相撞
                    b.goDead();//改变子弹状态为死的状态
                    f.goDead();//改变敌人状态为死的状态

                    //得分 得奖励
                    if(f instanceof  Award){
                        Award a=(Award) f;
                        int type=a.getAwardType();
                        //System.out.println("奖励类型："+type);
                        //如果type是0则奖励命（LIFE），是1则奖励火力值
                        //System.out.println("type的值为："+type);

                        switch(type){
                            case Award.ZERO://奖励命
                                hero.addLife();

                                break;
                            case Award.ONE://奖励火力值
                                hero.addDoubleFire();

                                break;

					/*default :
						hero.addDoubleFire();
						break;*/
                        }

                    }


                    if(f instanceof Score){//判断对象是不是得分类型
                        Score s=(Score)f;
                        score+=s.getScore();
                    }

                }

            }

            if(time>=20){
                /**子弹和boss以及和boss子弹相撞*/
                //遍历所有boss子弹
                for(int j=0;j<left.length;j++){
                    FlyingObject l=left[j];
                    if(b.isLife() && l.isLife() && l.hit(b)){
                        l.goDead();
                        b.goDead();
                    }
                }

                //中间子弹
                for(int k=0;k<mid.length;k++){
                    FlyingObject m=mid[k];
                    if(m.isLife() && b.isLife() && m.hit(b)){
                        m.goDead();
                        b.goDead();
                    }
                }

                //右边子弹
                for(int s=0;s<right.length;s++){
                    FlyingObject r=right[s];
                    if(r.isLife() && b.isLife() && r.hit(b)){
                        r.goDead();
                        b.goDead();
                    }
                }

                //子弹打中boss
                if(b.isLife() && boss.isLife() && b.hit(boss)){
                    boss.subBossLife();
                    b.goDead();
                    if(boss.getBossLife()==0){
                        boss.goDead();
                        state=VICTORY;
					/*Font zi=new Font("宋体",Font.TYPE1_FONT,25);//创建字体大小实例
					g.setFont(zi);
					g.setColor(Color.blue);//给画笔设置颜色
					g.drawString("******恭喜你击败了Boss******", 15, 325);//根据x,y坐标画字符串
				*/}
                }
            }



        }
    }








    //英雄机和敌人相撞
    public void heroBangAction(){
        for(int i=0;i<enemies.length;i++){//遍历所有敌机
            FlyingObject f=enemies[i];
            if(hero.isLife()&&f.isLife()&&f.hit(hero)){
                f.goDead();//改变敌人状态为死的状态
                //英雄机减命并清空火力值
                hero.clearDoubleFire();
                hero.subLife();

            }
        }
        //英雄机和boss的左子弹相撞
        for(int i=0;i<left.length;i++){
            FlyingObject l=left[i];
            if(hero.isLife() && l.isLife() && l.hit(hero)){
                l.goDead();
                hero.clearDoubleFire();
                hero.subLife();
            }
        }
        /**英雄机和右子弹相撞*/
        for(int i=0;i<right.length;i++){
            FlyingObject r=right[i];
            if(r.isLife() && hero.isLife() && r.hit(hero)){
                r.goDead();
                hero.clearDoubleFire();
                hero.subLife();
            }
        }

        /**英雄机和中间子弹相撞*/
        for(int i=0;i<mid.length;i++){
            FlyingObject m=mid[i];
            if(m.isLife() && hero.isLife() && m.hit(hero)){
                m.goDead();
                hero.clearDoubleFire();
                hero.subLife();
            }
        }

    }


    /*敌人移动*/
    public void stepAction(){
        //遍历敌人
        for(int i=0;i<enemies.length;i++){
            enemies[i].step();
        }

        for(int i=0;i<bullets.length;i++){
            bullets[i].step();
        }

        if(time>=20){//第十秒子弹开始移动
            //Boss移动
            boss.step();
            //遍历左边的子弹
            for(int i=0;i<left.length;i++){
                left[i].leftStep();
            }
            //遍历右边的子弹
            for(int i=0;i<right.length;i++){
                right[i].rightStep();
            }
            //遍历中间的子弹
            for(int i=0;i<mid.length;i++){
                mid[i].step();
            }

        }

    }

    /*检测游戏是否结束，如果结束把状态更换为游戏结束状态*/
    public void checkGameOver(){
        if(hero.getLife()<1){//如果生命值小于1，游戏结束
            state=GAME_OVER;
        }
    }

    /*处理飞行物越界问题*/
    public void flyingObjectOutAction() {
        //处理敌人越界问题
        int enemiesLivesIndex=0;
        FlyingObject[] enemiesLives=new FlyingObject[enemies.length];
        for(int i=0;i<enemies.length;i++){//遍历所有的敌人（包括越界和不越界）
            FlyingObject f=enemies[i];
            if(!f.flyingObjectOut() && !f.isRemove()){//不越界并没有被移除
                enemiesLives[enemiesLivesIndex++]=f;
            }
        }
        enemies=Arrays.copyOf(enemiesLives, enemiesLivesIndex);
        //System.out.println("处理后敌人数组的长度："+enemies.length);


        //处理子弹越界问题
        int bulletsLivesIndex=0;
        Bullet[] bulletsLives=new Bullet[bullets.length];
        for(int j=0;j<bullets.length;j++){
            Bullet b=bullets[j];
            if(!b.flyingObjectOut() && !b.isRemove()){
                bulletsLives[bulletsLivesIndex++]=b;
            }
        }
        bullets=Arrays.copyOf(bulletsLives,bulletsLivesIndex);
        //System.out.println("处理后的子弹不越界的数组长度："+bullets.length);


        //处理Boss子弹越界问题
        /**左边越界*/
        int BossBulletsLeftLivesIndex=0;
        BossBullet[] bossLeftLives=new BossBullet[left.length];
        for(int i=0;i<left.length;i++){
            BossBullet l=left[i];
            if(!l.flyingObjectOut() && !l.isRemove()){
                bossLeftLives[BossBulletsLeftLivesIndex++]=l;
            }
        }
        left=Arrays.copyOf(bossLeftLives,BossBulletsLeftLivesIndex );
        //System.out.println("处理后的左边子弹不越界的数组长度："+left.length);
        /**右边越界*/
        int BossBulletsRightLivesIndex=0;
        BossBullet[] bossRightLives=new BossBullet[right.length];
        for(int i=0;i<right.length;i++){
            BossBullet r=right[i];
            if(!r.flyingObjectOut() && !r.isRemove()){
                bossRightLives[BossBulletsRightLivesIndex++]=r;
            }
        }
        right=Arrays.copyOf(bossRightLives, BossBulletsRightLivesIndex);

        /**中间越界*/
        int BossBulletsMidLivesIndex=0;
        BossBullet[] bossMidLives=new BossBullet[mid.length];
        for(int i=0;i<mid.length;i++){
            BossBullet m=mid[i];
            if(!m.flyingObjectOut() && !m.isRemove()){
                bossMidLives[BossBulletsMidLivesIndex++]=m;
            }
        }
        mid=Arrays.copyOf(bossMidLives, BossBulletsMidLivesIndex);

    }



    public void action(){
        //创建鼠标侦听器
        MouseAdapter m=new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                if(state==RUNNING){
                    int x=e.getX();//获取鼠标的X坐标
                    int y=e.getY();//获取鼠标的y坐标
                    hero.moveStep(x, y);
                }
            }

            //鼠标点击事件
            public void mouseClicked(MouseEvent e){
                if(state == VICTORY){
                    state = START;
                }
                if(state==START){//如果是启动状态则把状态更换为运行状态
                    state=RUNNING;
                }
                if(state==GAME_OVER){//清理现场并将状态更换为启动状态

                    sky=new Sky();
                    hero=new Hero();
                    enemies=new FlyingObject[0];
                    bullets=new Bullet[0];
                    new_score=score;
                    //score=0;//清理分数
                    state=START;
                }

            }
            //鼠标移出事件
            public void mouseExited(MouseEvent e){
                if(state==RUNNING){//如果是运行状态则改为移出状态
                    state=PAUSE;
                }
            }

            //鼠标移入状态
            public void mouseEntered(MouseEvent e){
                if(state==PAUSE){
                    state=RUNNING;
                }
            }
        };
        //处理鼠标操作事件
        this.addMouseListener(m);

        //处理鼠标滑动事件

        this.addMouseMotionListener(m);


        //创建定时器

        Timer timer=new Timer();

        int interval=10;//作为后面的毫秒数
        timer.schedule(new TimerTask(){
            public void run(){//重写TimerTask抽象类中的抽象run()方法
                if(state==RUNNING){
                    sky.step();
                    enemiesAction();//敌人入场
                    stepAction();//敌人移动
                    BulletAction();//子弹入场
                    bulletBangAction();//子弹和敌人相撞
                    heroBangAction();//英雄机与敌人相撞
                    checkGameOver();//判断英雄机的生命值
                    flyingObjectOutAction();//处理飞行物越界问题
                    if(time>=10 && boss.isLife()){
                        BossLeftBulletAction();//左子弹入场
                        BossRightBulletAction();//右子弹入场
                        BossMidBulletAction();//中间子弹入场
                    }


                    if(timeIndex++%100==0){//每100*10毫秒time加一，以达到计时的作用
                        time+=1;
                    }




                }
                repaint();
            }


        }, interval,interval);//timer.schedule(语句,interval,interval)第一个点击后隔interval毫秒运行，第二个后面每隔interval毫秒运行一次

    }


    /*画对象*/
    public void paint(Graphics g){

        sky.paintObject(g);
        hero.paintObject(g);
        for(int i=0;i<enemies.length;i++){//遍历enemies数组，画出数组中的每一个对象
            enemies[i].paintObject(g);
        }

        for(int i=0;i<bullets.length;i++){
            bullets[i].paintObject(g);
        }


        if(time==18){
            Font zi=new Font("华文行楷",Font.TYPE1_FONT,35);//创建字体大小实例
            g.setFont(zi);
            g.setColor(Color.green);//给画笔设置颜色
            g.drawString("Boss即将出现！！！！！！", 50, 325);
        }

        if(time>=20){
            boss.paintObject(g);
            for(int i=0;i<left.length;i++){
                left[i].paintObject(g);
            }
            for(int i=0;i<mid.length;i++){
                mid[i].paintObject(g);
            }
            for(int i=0;i<right.length;i++){
                right[i].paintObject(g);
            }
        }

        Font zi=new Font("宋体",Font.TYPE1_FONT,25);//创建字体大小实例
        g.setFont(zi);
        g.setColor(Color.blue);//给画笔设置颜色
        g.drawString("生命:"+hero.getLife(), 15, 30);//根据x,y坐标画字符串
        g.drawString("火力值:"+hero.getDoubleFire(), 15, 55);
        g.drawString("分数:"+score, 15, 80);
        g.drawString("时间："+time, 15, 105);
        switch (state) {//根据不同的状态画不同的图片
            case START:
                g.drawImage(start,0,0,null);
                /*new Music();*/
                break;
            case PAUSE:
                g.drawImage(pause,0,0,null);
                break;
            case GAME_OVER:

                Boss boss=new Boss();
                g.drawImage(gameOver,0,0,null);
                g.drawString("你的分数为："+new_score, 125, 325);
                time=0;
                score=0;
                break;
            case VICTORY:
                g.drawImage(victory, 0, 0, null);
                g.drawString("你的分数为："+new_score, 125, 325);
                break;

        }

    }



    public static void main(String[] args){

        //创建窗口
        JFrame frame=new JFrame();
        World world=new World();//面板对象
        frame.add(world);//把面板放入在窗口（相框）中
        frame.setSize(WIDTH, HEIGHT);//设置窗口大小
        frame.setVisible(true);//设置窗口可见，尽快调用paint（）方法

        frame.setLocationRelativeTo(null);//设置窗口居中显示
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口即就是关闭程序


        world.action();
        while(true){
            new Music();
        }
    }
}
