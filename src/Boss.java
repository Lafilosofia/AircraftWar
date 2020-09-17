import java.awt.*;
import java.awt.image.BufferedImage;

public class Boss extends FlyingObject{
    public static BufferedImage image;
    public static BufferedImage[] bomsImages;
    private int xSpeed = 1;
    private int bossLifeNumber = 100;
    static {
        image = loadImage("de0.png");
        bomsImages = new BufferedImage[7];
        for (int i = 0;i < bomsImages.length;i ++){
            bomsImages[i] = loadImage("de" + i + ".png");
        }
    }

    public Boss(){
        super(162,153);
    }
    //Boss移动
    public void step(){
        y += ySpeed;
        x += xSpeedSpeed;
        if (y >= 0){
            y = 0;
        }
        if (this.x >= World.WIDTH - this.width || this.x <= 0){
            xSpeed *= -1;
        }
    }
    //画图片
    public void paintObject(Graphics g){
        g.drawImage(getImage(),this.x,this.y.null);
    }
    //根据不同状态来获取Boss图片
    int bomsIndex = 0;
    public BufferedImage getImage(){
        if (isLife()){
            return image;
        }else if (isDead()){
            BufferedImage img = bomsImages[bomsImages ++];
            if (bomsIndex == bomsImages.length){
                state = REMOVE;
            }
            return img;
        }
        return null;
    }
    //发射子弹
    //Boss发射子弹
    //从左边发射
    public BossBullet bossLeftShoot(){
        int xStep = this.width / 6;
        int yStep = 10;
        BossBullet bb = new BossBullet(this.x + 1 * xStep,this.y + this.height + yStep);
        return bb;
    }
    //从右边发射子弹
    public BossBullet bossRightShoot(){
        int sStep = this.width / 6;
        int yStep = 10;
        BossBullet bb = new BossBullet(this.x + 6 * xStep,this.y + this.height + yStep);
        return bb;
    }
    //从中间发射子弹
    public BossBullet bossMidShoot(){
        int xStep = this.width / 6;
        int yStep = 10;
        BossBullet bb = new BossBullet(this.x + 3 * xStep,this.y + this.height + yStep);
        return bb;
    }
    public boolean flyingObjectOut(){
        return false;
    }
    //Boss减命
    public void subBossLife(){
        bossLifeNumber -= 1;
    }
    //获得boss的命
    public int getBossLife(){
        return bossLifeNumber;
    }
}
