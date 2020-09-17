
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 飞行物
 */
public abstract class FlyingObject {

    protected int height;
    protected  int width;
    protected int x;
    protected int y;

    //飞机状态
    public static final int LIFE=0;//huozhezhuagntai
    public static final int DEAD=1;//死掉状态
    public static final int REMOVE=2;//移除状态
    public int state=LIFE;//生命值开始值

    /*
     * 小敌机，大敌机，直升机初始化
     */

    public FlyingObject(int width,int height){
        this.width=width;
        this.height=height;
        Random random=new Random();
        //敌机x坐标在面板宽带和敌机自身的宽度差（面板宽度-自身宽度）
        this.x=random.nextInt(World.WIDTH-this.width);//0~~~~450-this.width
        this.y=-height;
    }

    /*
     * 天空，子弹，英雄机初始化
     */

    public FlyingObject(int width,int height,int x,int y){
        this.width=width;
        this.height=height;
        this.x=x;
        this.y=y;
    }
    /*飞行物移动方法*/
    public abstract void step();

    /**
     * 读取图片BufferedIMage 是图片类型
     *
     */


    public static BufferedImage loadImage(String fileName) {

        try {//正常的程序，如果try里面出现异常
            //catch会捕捉异常，如果异常，则运行
            //catch里面的语句

            BufferedImage image=ImageIO.read(FlyingObject.class.getResource(fileName));//fileName资源名，同包中读取方式
            return image;

        } catch (Exception e) {
            //e.printStackTrace();//日志跟踪，问题出现在哪里
            throw new RuntimeException("读取图片失败");
            //抛出运行时问题,然后直接跳出方法，不往后运行
        }


    }



    /*
     * ：判断对象是否活着*/
    public boolean isLife(){
        return state==LIFE;
    }
    //判断对象是否死掉
    public boolean isDead(){
        return state==DEAD;
    }
    //判断对象是否移除
    public boolean isRemove(){
        return state==REMOVE;
    }


    /*根据状态获取图片*/
    public abstract BufferedImage getImage();

    /**画对象  g:画笔**/
    public  void paintObject(Graphics g){
        g.drawImage(getImage(),this.x,this.y,null);

    }



    /**英雄机和子弹相撞*：other代表英雄机/子弹*/
    public boolean hit(FlyingObject other){
        int x=other.x;
        int y=other.y;
        int x1=this.x-other.width;
        int x2=this.x+this.width;
        int y1=this.y-other.height;
        int y2=this.y+this.height;
        return x>=x1&&x<=x2&&y>=y1&&y<=y2;//ture则相撞，否则没撞
    }


    /*敌人死亡*/

    public void goDead(){
        state=DEAD;
    }

    /*检测飞行物是否越界*/
    public abstract boolean flyingObjectOut();
}
