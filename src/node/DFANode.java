package node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/24.
 * DFA节点类
 */
public class DFANode {

    private int x , y;  //节点的x，y坐标

    private int state;  //节点所代表的状态number

    //保存每个节点之间的关系map
    private Map <Character,DFANode> relateMap = new HashMap<Character,DFANode>();

    public static double radius = 20.0;//默认半径

    public DFANode(){}

    public DFANode(int state) {
        this.state = state;
    }

    public DFANode(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Map<Character, DFANode> getRelateMap() {
        return relateMap;
    }

    public void setRelateMap(Map<Character, DFANode> relateMap) {
        this.relateMap = relateMap;
    }
    @Override
    public String toString() {

        String str = "DFANode{state=" + state + ",x:" + x + ",y:" + y + ";";

        for (Map.Entry<Character, DFANode> entry : relateMap.entrySet()){
            str += "-->" + entry.getKey() + "--->" + entry.getValue().getState() + ",";
        }
        str += "}";

        return str;
    }
}
