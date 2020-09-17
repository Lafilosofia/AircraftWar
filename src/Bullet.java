import java.awt.image.BufferedImage;

/**
 * 子弹
 */
public class Bullet extends FlyingObject{
    private int speed;
    //初始化
    public static BufferedImage image;
    static {
        image = loadImage("bullet.png");
    }

    public Bullet(int x,int y){
        super(8,20,x,y);
        speed = 3;
    }

    public void step(){
        y -= speed;
        System.out.println("子弹y移动了" + y + "子弹移动了" + x);
    }
    //根据不同状态获取子弹图片
    public BufferedImage getImage(){
        if (isLife()){
            return image;
        }else if(isDead()){
            state = REMOVE;
        }
        return null;
    }
    //检测越界
    public boolean flyingObjectOut(){
        return this.y <= this.height();
    }
}
