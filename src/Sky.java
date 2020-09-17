import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * 天空
 */

public class Sky extends FlyingObject{
    private int speed;
    private int y1;//y与y1为两张图片


    public static BufferedImage image;

    static{
        image=loadImage("bg4.png");
    }
    //初始化
    public Sky(){
        super(World.WIDTH, World.HEIGHT, 0, 0);
        y1=-this.height;
        speed=1;
    }
    //移动
    public void step(){
        y+=speed;
        y1+=speed;
        if(y>=World.HEIGHT){//y坐标大于窗口高度
            y=-this.height;
        }
        if(y1>=World.HEIGHT){//y1坐标大于窗口高度
            y1=-this.height;
        }
        //System.out.println("天空移动了"+y+",y1移动了："+y1);
    }

    /*获取天空的背景图*/
    public BufferedImage getImage(){
        return image;
    }
    /*天空重写方法（目的把y1也要画上）*/
    public void paintObject(Graphics g){
        g.drawImage(getImage(), this.x, this.y, null);
        g.drawImage(getImage(), this.x, this.y1, null);
    }
    /*天空不越界*/
    public boolean flyingObjectOut() {
        // TODO Auto-generated method stub
        return false;//不越界
    }
}
