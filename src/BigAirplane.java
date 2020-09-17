import java.awt.image.BufferedImage;

/**
 * 大敌机
 */
public class BigAirplane {
    private int speed;  //速度
    public static BufferedImage image;  //静态成员变量 大敌机图片
    public static BufferedImage[] bomsImages;   //爆炸后图片按钮
    static{     //加载大敌机图片和爆炸后的效果图
        image = loadImage("BigAirplane.png");
        System.out.println("大敌机的图片" + image);
        bomsImages = new BufferedImage[6];
        for (int i= 0;i < bomsImages.length;i ++){
            bomsImages[i] = loadImage("boms" + i + ".png");
            System.out.println("第" + i + "张图片的效果图:" + bomsImages[i]);
        }
    }
    //初始化
    public BigAirplane(){
        super(94,84);
        speed = 2;
    }
    //移动
    public void step(){
        y += speed;
        System.out.println("大敌机移动了" + y + "x移动了" + x);
        System.out.println();
    }
    //根据不同状态获取大敌机不同的图片
    int bomsIndex = 0;
    public BufferedImage getImage(){
        if (isLife()){
            return image;
        }else if(isDead()){
            BufferedImage img = bomsImages[bomsIndex ++];
            if (bomsIndex == bomsImages.length){
                state = REMOVE;
            }
            return img;
        }
        return null;
    }

    public int getScore(){
        return  Score.THREE;
    }

    public boolean flyingObjectOut(){
        return this.y >= World.HEIGHT;
    }
}
