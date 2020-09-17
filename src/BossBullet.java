import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Boss子弹
 */
public class BossBullet {
    private int leftSpeed = 3;
    private int rightSpeed = 3;
    public static BufferedImage image;
    static {
        image = loadImage("bossBullet.png");
    }
    //初始化
    public BossBullet(int x,int y){
        super(36,36,x,y);
    }
    //左子弹移动方法
    public void leftStep(){
        x -= leftStep();
        y += speed;
        if(x <= 0 || x >= World.WIDTH - this.width){
            leftSpeed *= -1;
        }
    }
    //中间子弹移动方法
    public void step(){
        y += speed;
    }
    //右子弹移动方法
    public void rightStep(){
        x += rightSpeed;
        y += speed;
        if (x >= World.WIDTH - this.width || x <= 0){
            rightSpeed *= -1;
        }
    }
    //根据不同状态获取子弹图片
    public BufferedImage getImage(){
        if (isLife()){
            return image;
        }else if (isDead()){
            state = REMOVE;
        }
        return null;
    }
    //画子弹
    public void paintObject(Graphics g){
        g.drawImage(getImage(),this.x,this.y,null);
    }
}
