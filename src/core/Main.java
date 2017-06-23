package core;

import init.Init;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    //宽
    private final static int WIDE = 900;
    //高
    private final static int HIGHT = 500;



    /**
     * javaFX应用程序将UI容器定义为舞台（Stage）与场景（Scene）.Stage是javaFX顶级容器，Scene类是所有内容的容器
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane,WIDE, HIGHT);
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

        for(int i = 0; i < n+1; i++){
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

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Node> children = gridPane.getChildren();
                int a[][] = new int[n][m];
                for(Node node: children){
                    if(node instanceof TextField){
                        TextField tx = (TextField)node;

                    }
                }

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < m; j++){
                        System.out.print(a[i][j] + "");
                        System.out.println();
                    }
                }
            }
        });

        borderPane.requestLayout();
    }

    /**
     * 生成图像的面板
     */
    public void createImage(){

    }


    /**
     * 校验面板
     */
    public void checkString(){

    }


    public static void main(String[] args) {
        launch(args);
    }
}
