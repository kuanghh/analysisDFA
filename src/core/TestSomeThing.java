package core;

import init.Init;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import node.DFANode;

/**
 * Created by Administrator on 2017/6/24.
 */
public class TestSomeThing extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();

        Group group = new Group();
        Canvas canvas = new Canvas(300,200);
        group.getChildren().add(canvas);
        pane.setCenter(group);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        double x1 = 60;
        double y1 = 60;

        double x2 = 200;
        double y2 = 60;

        gc.fillText("1",x1,y1);
        gc.fillText("2",x2,y2);

        gc.strokeArc(x1- DFANode.radius,y1 - DFANode.radius,60,60,45,240, ArcType.OPEN);


//        double[] xpoints = {x1,(x1+x2) / 2 ,x2};
//        double[] ypoints = {y1,(y1+y2) /2 -15,y2};
//        gc.strokePolygon(xpoints,ypoints,3);
//
        Scene scene = new Scene(pane,300,200);
        scene.getStylesheets().add(Init.SKINURL);


        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DFA");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
