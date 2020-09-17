import java.awt.image.BufferedImage;

/**
 * 英雄机
 */

public class Hero extends FlyingObject{
    private int DoubleFire;//火力值
    private int life;//生命值

    public static BufferedImage[] images;

    static{
        images=new BufferedImage[2];
        for(int i=0;i<images.length;i++){
            images[i]=loadImage("hero"+i+".png");
        }
    }

    //初始化
    public Hero(){
        super(97, 139,140 ,420 );
        DoubleFire=0;
        life=3;
    }

    //英雄机的移动是随着鼠标而移动的
    public void moveStep(int x,int y){
        this.x =x-this.width/2;
        this.y =y-this.height/2;
    }


    //重写父类的抽象方法，由于Hero移动方式特殊
    //所以重写此方法(需要传送鼠标的x,y参数，只是为了配合父类中的抽象方法，这里不管它的方法体)
    public void step(){

    }

    /*根据不同状态获取英雄机不同的图片*/
    int bomsIndex=0;
    public BufferedImage getImage(){
        if(isLife()){
            BufferedImage img=images[bomsIndex++%2];//余二让其下标一直为2,1
            return img;
        }else if(isDead()){
            state=REMOVE;
        }
        return null;
    }


	/*public Bullet[] shoot(){
		Bullet[] bs=new Bullet[World.WIDTH];
		for(int i=0;i<bs.length;i++){
			bs[i]=new Bullet(i,500);
					}
		return bs;
	}*/




    /*英雄机发射子弹*/
    public Bullet[] shoot(){
        int xStep=this.width/4;
        int yStep=20;
        if(DoubleFire>0&&DoubleFire<=80){//双倍火力
            Bullet[] bullet=new Bullet[2];
            //在英雄机的1/4处发射子弹
            bullet[0]=new Bullet(this.x+xStep,this.y-yStep);
            //在英雄机的3/4处发射子弹
            bullet[1]=new Bullet(this.x+3*xStep,this.y-yStep);
            DoubleFire-=2;//子弹发射一次双倍火力，则火力值减去2
            return bullet;

        }else if(DoubleFire>80){//单倍火力
            Bullet[] bu=new Bullet[3];
            bu[0]=new Bullet(this.x,this.y -yStep);
            bu[1]=new Bullet(this.x+2*xStep,this.y -yStep);
            bu[2]=new Bullet(this.x+4*xStep,this.y -yStep);
            DoubleFire-=3;
            return bu;
        }else{
            Bullet[] b=new Bullet[1];
            b[0]=new Bullet(this.x+2*xStep,this.y-yStep);
            return b;
        }

    }

    /*获取英雄机的命*/
    public int getLife(){
        return life;
    }

    /*增加英雄机命*/
    public void addLife(){
        life++;
    }

    /*英雄机减少命*/
    public void subLife(){
        life--;
    }

    /*清空英雄机的火力值*/
    public void clearDoubleFire(){
        DoubleFire=0;
    }

    /*增加火力值*/
    public void addDoubleFire(){
        DoubleFire+=48;
    }

    /*获取火力值*/
    public int getDoubleFire(){
        return DoubleFire;
    }

    /**英雄机不越界*/
    public boolean flyingObjectOut() {
        // TODO Auto-generated method stub
        return false;//false为不越界
    }
}
