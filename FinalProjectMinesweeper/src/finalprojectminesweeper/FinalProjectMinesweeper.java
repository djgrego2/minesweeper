package finalprojectminesweeper;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    
    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        
        for(int y = 0; y < Y_TILES; y++){
            for(int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x, y, Math.random()< 0.2);
                
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }
        
        for(int y = 0; y < Y_TILES; y++){
            for(int x = 0; x < X_TILES; x++){
                Tile tile = grid[x][y];
                // set bombs
                
                if(tile.hasBomb)
                    continue;
                
                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
                
                if(bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }
        
        return root;
    }
    
    private List<Tile> getNeighbors(Tile tile){
        List<Tile> neighbors = new ArrayList<>();
        
        int[] points = new int[]{
            -1, 1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            -1, -1,
            1, 1,
            1, 0,
            1, 1
        };
        
        for(int i = 0; i < points.length; i++){
            int dx = points[i];
            int dy = points[++i];
            
            int newX = tile.x + dx;
            int newY = tile.y + dy;
            
            if(newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES){
                neighbors.add(grid[newX][newY]);
            }
                    
        }
        
        return neighbors;
    }
    
    private class Tile extends StackPane{
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;
        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();
        
        public Tile(int x, int y, boolean hasBomb){
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            
            border.setStroke(Color.LIGHTGREY);
            
            text.setFill(Color.WHITE);
            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);
            
            getChildren().addAll(border, text);
            
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);
            
            setOnMouseClicked(e -> open());
        }
        
        public void open() {
            if(isOpen)
                return;
            
            isOpen = true;
            text.setVisible(true);
            border.setFill(null);
            
            if(text.getText().isEmpty()){
                getNeighbors(this).forEach(Tile::open);
            }
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
