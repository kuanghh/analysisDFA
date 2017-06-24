package node;

import core.Main;

/**
 * Created by Administrator on 2017/6/24.
 */
public class NodeLocation {

    private volatile static int num = 0;

    private static int PreX = 60;
    private static int PreY = 60;

    private static int x ;
    private static int y ;

    private static double Redius = DFANode.radius;

    private static int MaxWide = Main.WIDE/2;
    private static int MaxHeight = Main.HEIGHT;

    public synchronized static int getX(){

        if(num % 2 == 1){
            x += 100 + Redius*2;
        }else{
            x = PreX;
        }

        return x;
    }

    public synchronized static int getY(){

        if(num % 2 == 0){
            if(num != 0){
                y += (100 + Redius*2);
            }else{
                y = 60;
            }
            PreY = y;
        }else{
            y = PreY;
        }
        num += 1;
        return y;
    }
}
