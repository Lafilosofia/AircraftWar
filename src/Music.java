import java.io.FileInputStream;
import java.io.BufferedInputStream;
import javazoom.jl.player.Player;


public class Music {
    static{//mp3初始化
        try {
            FileInputStream in=new FileInputStream("src/周杰伦.mp3");
            BufferedInputStream bis =new BufferedInputStream(in);
            Player p=new Player(bis);
            System.out.println("正在播放：Victory123");
            p.play();

        } catch(Exception e) {
            //e.printStackTrace();//日志跟踪
            throw new RuntimeException("加载失败！");
        }
    }

    public static void main(String[] args){
        new Music();
    }
}
