package core;

import init.Init;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import node.DFANode;
import node.NodeLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    //宽
    public final static int WIDE = 900;
    //高
    public final static int HEIGHT = 500;



    /**
     * javaFX应用程序将UI容器定义为舞台（Stage）与场景（Scene）.Stage是javaFX顶级容器，Scene类是所有内容的容器
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane,WIDE, HEIGHT);
        scene.getStylesheets().add(Init.SKINURL);

        //初始化
        ensureNumbersOfStateAndParam(pane);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DFA");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * 确认参数个数和状态个数的面板
     */
    public void ensureNumbersOfStateAndParam(BorderPane borderPane){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);  //对齐方式:默认靠左对齐，当前居中对齐
        gridPane.setHgap(10);//设置水平间隔
        gridPane.setVgap(10);//设置垂直间隔
        gridPane.setPadding(new Insets(25,25,25,25));//设置padding 顺序：上\右\下\左
        borderPane.setCenter(gridPane);


        Label l1 = new Label("请输入状态个数(默认从0开始):");
        l1.setPrefSize(180,10);
        Label l2 = new Label("请输入参数个数(默认从a开始):");
        l2.setPrefSize(180,10);

        TextField tf1 = new TextField();
        tf1.setPrefSize(30,10);
        TextField tf2 = new TextField();
        tf2.setPrefSize(30,10);

        Button button = new Button("确认");
        button.setPrefSize(50,25);

        gridPane.add(l1,0,1); //放到第0列，第一行
        gridPane.add(l2,0,2);
        gridPane.add(tf1,1,1);
        gridPane.add(tf2,1,2);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(button);
        gridPane.add(hbox,1,4);


        //帮button添加事件
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String stateNumStr = tf1.getText(); //获取状态的个数
                String paramNumStr = tf2.getText();  //获取参数的个数

                Integer stateNum = 0;
                Integer paramNum = 0;
                try{
                    stateNum = Integer.valueOf(stateNumStr);
                    paramNum = Integer.valueOf(paramNumStr);
                }catch (Exception e){
                    System.out.println("参数或者状态个数输入错误");
                }
                //确认所有参数的面板
                ensureDFAParam(stateNum,paramNum,borderPane);
            }
        });
    }

    /**
     * 确认所有参数的面板
     */
    public void ensureDFAParam(int stateNum, int paramNum, BorderPane borderPane){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);  //对齐方式:默认靠左对齐，当前居中对齐
        gridPane.setHgap(30);//设置水平间隔
        gridPane.setVgap(30);//设置垂直间隔
        gridPane.setPadding(new Insets(30,30,30,30));//设置padding 顺序：上\右\下\左
        borderPane.setCenter(gridPane);


        int n = stateNum;  //行数
        int m = paramNum;  //列数

        for(int i = 0; i < n; i++){
            Label l = new Label("状态" + i + ":");
            l.setPrefSize(60,10);
            gridPane.add(l,0,i+1);
        }
        char c = 'a';
        for(int j = 0; j < m; j++){
            Label l = new Label("参数" + (char)(c + j) );
            l.setPrefSize(60,10);
            gridPane.add(l,j+1,0);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                TextField tx = new TextField();
                tx.setPrefSize(40,10);
                gridPane.add(tx,j+1,i+1);
            }
        }

        Button button = new Button("生成DFA图");
        button.setPrefSize(100,25);
        gridPane.add(button,m+2,n/2);
        Label tic = new Label("提示:左边的"+stateNum+"行，每行有"+paramNum+"个整数。" + "\n" +
                "其中第i行第j列的数字k，表示DFA在状态i-1，" + "\n" +
                "当输入符号为第j个小写字母时，迁移到状态k");
        tic.setMaxWidth(300);
        tic.setMaxHeight(400);
        gridPane.add(tic,m+2,n/2+1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Node> children = gridPane.getChildren();
                int[][] a = new int[n][m];  //存放数据的
                int i = 0,j = 0;
                //获取数据并保存到数组中
                for(Node node: children){
                    if(node instanceof TextField){
                        TextField tx = (TextField)node;
                        if(tx.getText() == null || tx.getText().length() == 0){
                            tx.setText("-1");
                        }
                        a[i][j] = Integer.parseInt(tx.getText());
                        j++;
                        if(j == m){
                            j = 0;
                            i++;
                            if(i == n) break;
                        }
                    }
                }
                //生成图像的面板
                createImage(a,borderPane);
            }
        });
        borderPane.requestLayout();
    }

    /**
     * 生成图像的面板
     * @param a
     * @param borderPane
     */
    public void createImage(int[][] a, BorderPane borderPane){
        List<DFANode> nodeList = new ArrayList<>(); //保存所有节点的集合
        int n = a.length;//状态个数
        int m = a[0].length;//参数个数

        //1创建所有节点
        for(int i = 0; i < n; i++){
            DFANode node = new DFANode(NodeLocation.getX(),NodeLocation.getY(),i);
            nodeList.add(node);
        }

        //2为所有节点设定关系
        for(int i = 0; i < n; i++){
            DFANode dnode = nodeList.get(i);
            for(int j = 0; j < m; j++){
                if(a[i][j] == -1){
                    continue;
                }
                DFANode node = nodeList.get(a[i][j]);
                dnode.getRelateMap().put((char)('a' + j),node);
            }
        }

        //3.绘画图像
        Group group = new Group();
        Canvas canvas = new Canvas(WIDE / 2,HEIGHT);
        group.getChildren().add(canvas);
        borderPane.setLeft(group);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        //3.1画圆
        for(DFANode node : nodeList){
           gc.strokeOval(node.getX(),node.getY(),DFANode.radius*2,DFANode.radius*2);
           gc.fillText(String.valueOf(node.getState()),node.getX()+DFANode.radius,node.getY()+DFANode.radius);
        }

        //3.2画线
        for(DFANode node : nodeList){
            Map<Character, DFANode> map = node.getRelateMap();
            for(Map.Entry<Character, DFANode> entry : map.entrySet()){
                gc.setLineWidth(1);
                DFANode nd = entry.getValue();
                Character c = entry.getKey();
                double radius = DFANode.radius;

                //起始圆的圆心
                double startX = node.getX() + radius;
                double startY = node.getY() + radius;

                //目标圆的圆心
                double endX = nd.getX() + radius;
                double endY = nd.getY() + radius;

                if(node.getState() != nd.getState()){  //如果不是自身到达自身的话
                    //求两点之间的距离
                    double distance = Math.sqrt(Math.pow(endX - startX,2) + Math.pow(endY - startY,2));
                    double xa = endX - radius/distance *(endX - startX);
                    double ya = endY - radius/distance *(endY - startY);


                    //画线
                    if(node.getState() < nd.getState()){ // 如果起始状态比目标状态小
                        //中间点
                        double xc = (startX + xa)/2 - 15;
                        double yc = (startY + ya)/2 - 15;
                        gc.strokeLine(startX,startY,xc,yc);
                        gc.strokeLine(xc,yc,xa,ya);

                        double k = (ya- yc) / (xa -xc);

                        double k1 = k + 2;
                        double k2 = k - 2;

                        double len = 100;
                        double f1x;
                        double f1y;
                        double f2x;
                        double f2y;
                        if(xc < xa){//向右
                             f1x = xa - Math.sqrt(len/(Math.pow(k1,2)+1));
                             f1y = ya - k1 * Math.sqrt(len/(Math.pow(k1,2)+1));
                             f2x = xa - Math.sqrt(len/(Math.pow(k2,2)+1));
                             f2y = ya - k2 * Math.sqrt(len/(Math.pow(k2,2)+1));
                        }else{//向左
                             f1x = xa + Math.sqrt(len/(Math.pow(k1,2)+1));
                             f1y = ya + k1 * Math.sqrt(len/(Math.pow(k1,2)+1));
                             f2x = xa + Math.sqrt(len/(Math.pow(k2,2)+1));
                             f2y = ya + k2 * Math.sqrt(len/(Math.pow(k2,2)+1));
                        }


                        gc.strokeLine(xa,ya,f1x,f1y);
                        gc.strokeLine(xa,ya,f2x,f2y);
                        gc.fillText(String.valueOf(c),xc,yc);
                    }else{ //起始状态比目标状态大
                        //中间点
                        double xc = (startX + xa)/2 + 20;
                        double yc = (startY + ya)/2 + 20;
                        gc.strokeLine(startX,startY,xc,yc);
                        gc.strokeLine(xc,yc,xa,ya);
                        gc.fillText(String.valueOf(c),xc,yc);

                        double k = (ya- yc) / (xa -xc);

                        double k1 = k + 2;
                        double k2 = k - 2;
                        double len = 100;
                        double f1x;
                        double f1y;
                        double f2x;
                        double f2y;
                        if(xc < xa){//向右
                            f1x = xa - Math.sqrt(len/(Math.pow(k1,2)+1));
                            f1y = ya - k1 * Math.sqrt(len/(Math.pow(k1,2)+1));
                            f2x = xa - Math.sqrt(len/(Math.pow(k2,2)+1));
                            f2y = ya - k2 * Math.sqrt(len/(Math.pow(k2,2)+1));
                        }else{//向左
                            f1x = xa + Math.sqrt(len/(Math.pow(k1,2)+1));
                            f1y = ya + k1 * Math.sqrt(len/(Math.pow(k1,2)+1));
                            f2x = xa + Math.sqrt(len/(Math.pow(k2,2)+1));
                            f2y = ya + k2 * Math.sqrt(len/(Math.pow(k2,2)+1));
                        }

                        gc.strokeLine(xa,ya,f1x,f1y);
                        gc.strokeLine(xa,ya,f2x,f2y);
                    }

                }else{   //自身到达自身

                    gc.strokeLine(startX,startY,startX,startY-radius*1.5);
                    gc.strokeLine(startX,startY-radius*1.5,startX+radius,startY-radius*1.5);
                    gc.strokeLine(startX+radius,startY-radius*1.5,startX+radius,startY);


                    //画字母
                    gc.fillText(String.valueOf(c),(startX*2+radius)/2,startY-radius*1.5);

                }
            }
        }

        //绘画右边的验证面板
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);  //对齐方式:默认靠左对齐，当前居中对齐
        gridPane.setHgap(30);//设置水平间隔
        gridPane.setVgap(30);//设置垂直间隔
        gridPane.setPadding(new Insets(30,30,30,30));//设置padding 顺序：上\右\下\左
        borderPane.setCenter(gridPane);

        Label l0 = new Label("请输入可以接受的状态，以,隔开");
        TextField t0 = new TextField();

        Label l1 = new Label("请输入要验证的字符串");
        TextField t1 = new TextField();
        Button btn = new Button("验证");

        gridPane.add(l0,1,0);
        gridPane.add(t0,1,1);
        gridPane.add(l1,1,2);
        gridPane.add(t1,1,3);
        gridPane.add(btn,1,4);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //获取接受状态
                String acceptStr = t0.getText();
                if(acceptStr == null || acceptStr.length() == 0) return;
                String[] acceptArr = acceptStr.split(",");
                List<Integer> acceptList = new ArrayList<Integer>();
                for (int i = 0; i < acceptArr.length; i++) {
                    acceptList.add(Integer.valueOf(acceptArr[i]));
                }
                //获取字符串
                String text = t1.getText();
                if(text == null || text.length() == 0) return;
                checkStr(text,acceptList,borderPane,nodeList);
            }
        });

        borderPane.requestLayout();
    }


    /**
     * 检测字符串是否成立
     * @param text
     * @param borderPane
     */
    public void checkStr(String text,List<Integer> acceptList,BorderPane borderPane,List<DFANode> nodeList){

        int length = text.length();
        boolean flag = true;

        //从0状态开始校验
        DFANode dfaNode = nodeList.get(0);
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            Map<Character, DFANode> map = dfaNode.getRelateMap();
            if(map.containsKey(c)){
                DFANode n = map.get(c);
                dfaNode = n;
            }else{
                flag = false;
                break;
            }
        }
        if(flag){
            if(acceptList.contains(dfaNode.getState())){
                flag = true;
            }else{
                flag = false;
            }
        }

        GridPane gridPane = (GridPane) borderPane.getCenter();
        if(gridPane.getChildren().size() == 6){
            gridPane.getChildren().remove(5);
        }
        Label l = new Label();
        if(flag){
            l.setText("验证成功");
            l.setTextFill(Color.GREEN);
        }else{
            l.setText("验证失败");
            l.setTextFill(Color.RED);
        }
        gridPane.add(l,1,5);
        gridPane.requestLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
