package finalprojectminesweeper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author GAustin6
 */
public class FinalProjectMinesweeper extends Application {
    
    private static final int TILE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;
    
    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;
    
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        
        return root;
    }
    
    private class Tile extends StackPane{
        private int x, y;
        private boolean hasBomb;
        
        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();
        
        public Tile(int x, int y, boolean hasBomb){
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            
            border.setStroke(Color.LIGHTGREY);
            
            text.setText(hasBomb ? "X" : "");
            
            getChildren().addAll(border, text);
            
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(createContent());
        
        stage.setScene(scene);
        stage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }
    
}
