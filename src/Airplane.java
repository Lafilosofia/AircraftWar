import java.awt.image.BufferedImage;

/**
 * 小敌机
 */
public class Airplane {
    private int speed;//速度

    public static BufferedImage image;
    public static BufferedImage[] bomsImages;

    static{
        image  = loadImage("ariplane.png");

        bomsImages = new BufferedImage[6];
        for(int i = 0;i < bomsImages.length;i ++){
            bomsImages[i] = loadImage("boms" + i +".png");
        }
    }
    //初始化
    public Airplane(){
        super(72, 82);
        speed = 2;

    }




    //移动

    public void step(){
        y += speed;
        //System.out.println("小敌机y移动了"+y+"x移动了"+x);
        System.out.println();
    }

    /**
     * 根据不同的状态获取大敌机不同的图片
     */
    int bomsIndex = 0;
    public BufferedImage getImage(){
        if(isLife()){
            return image;
        }else if(isDead()){
            BufferedImage img = bomsImages[bomsIndex ++];
            if(bomsIndex == bomsImages.length){
                state = REMOVE;
            }
            return img;
        }
        return null;
    }




    /*小敌机得分*/
    public int getScore() {

        return Score.ONE;
    }




    /*检测小敌机是否越界*/
    public boolean flyingObjectOut() {

        return this.y >= World.HEIGHT;
    }
}
