import java.awt.image.BufferedImage;

/**
 * 直升机
 */

public class Helicopter extends FlyingObject implements Award{
    private int xSpeed;
    private int ySpeed;

    public static BufferedImage image;
    public static BufferedImage[] bomsImages;
    public int awardType;//奖励类型

    static{
        image =loadImage("Helicopter.png");

        bomsImages=new BufferedImage[6];
        for(int i=0;i<bomsImages.length;i++){
            bomsImages[i]=loadImage("boms"+i+".png");
        }
    }

    //初始化
    public Helicopter(){
        super(61, 69);
        xSpeed=2;
        ySpeed=2;
        awardType=(int)(Math.random()*2);//奖励类型0或1

    }




    //移动
    public void step(){
        x+=xSpeed;
        y+=ySpeed;
        if(x>=World.WIDTH-this.width||x<=0){
            xSpeed*=-1;
        }
        //System.out.println("直升机x移动了"+x+"直升机y移动了"+y);
    }




    /*根据不同状态获取直升机的图片*/
    int bomsIndex=0;
    public BufferedImage getImage(){
        if(isLife()){
            return image;
        }else if(isDead()){
            BufferedImage img=bomsImages[bomsIndex++];
            if(bomsIndex==bomsImages.length){
                state=REMOVE;
            }
        }
        return null;
    }
    /**获取奖励类型*/
    public int getAwardType(){
        return awardType;
    }




    /**检测越界*/
    public boolean flyingObjectOut() {
        // TODO Auto-generated method stub
        return this.y >=World.HEIGHT;
    }
}
