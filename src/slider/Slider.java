package slider;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.collections.ObservableList;
import javafx.stage.*;

import javafx.stage.Stage;

public class Slider extends Application {
    final int num = 4;   //NUM x NUM grid

    class Point {

        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    BorderPane empty;

    void movePane(MouseEvent event) {
        BorderPane sp = (BorderPane)event.getSource();
        Point index = new Point(GridPane.getColumnIndex(sp),GridPane.getRowIndex(sp));
        Point eindex = new Point(GridPane.getColumnIndex(empty),GridPane.getRowIndex(empty));
        if(isPossible(index,eindex))
        {
            ImageView iv = (ImageView) event.getTarget();
            sp.getChildren().remove(iv);
            empty.getChildren().clear();
            empty.getChildren().add(iv);
            empty = sp;
        }
    }
    boolean isPossible(Point index, Point empty) {
        if (Math.abs(index.x - empty.x) == 1 && index.y == empty.y) {
            return true;
        } else if (Math.abs(index.y - empty.y) == 1 && index.x == empty.x) {
            return true;
        } else {
            return false;
        }
    }
    int randInteger(ArrayList<Integer> numbers)
    {
        Random generator = new Random();  
        int index = generator.nextInt(numbers.size());
        int item = numbers.get(index);
        numbers.remove(index);
        return item;
    };
    @Override
    public void start(Stage stage) throws Exception {
        GridPane gp = new GridPane();
        for (int i = 0; i < num; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            RowConstraints rowConst = new RowConstraints();
            colConst.setPercentWidth(100. / num);
            rowConst.setPercentHeight(100. / num);

            gp.getColumnConstraints().add(colConst);
            gp.getRowConstraints().add(rowConst);
        }
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 1; i < num*num; i++)
            numbers.add(i);
        
        for (int i = 0; i < num; i++) {

            for (int j = 0; j < num; j++) {
                BorderPane sp = new BorderPane();
                ImageView iv = new ImageView();
                if (i + 1 != num || j + 1 != num) {
                    
                    
                    int item = randInteger(numbers);
                    Image image = new Image("File:src\\slider\\img\\" + item + ".jpg");
                    iv = new ImageView(image);
                    //iv.setMouseTransparent(true);
                    
                }
                //iv.fitWidthProperty().bind(sp.widthProperty());
                
                iv.fitWidthProperty().bind(sp.widthProperty()); 
                iv.fitHeightProperty().bind(sp.heightProperty());
                sp.getChildren().add(iv);
                sp.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                    movePane(e);
                });
                empty = sp;
                GridPane.setConstraints(sp, i, j);
                gp.getChildren().add(sp);

            }
        }
        Scene scene = new Scene(gp);
        stage.setTitle("Slider Game");
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent event) {
                Platform.exit();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
