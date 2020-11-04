import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Stack;

public class UIManager {
    private static UIManager instance;

    public static UIManager getInstance() {
        if (instance == null) instance = new UIManager();
        return instance;
    }

    private StackPane gameStackPane;
    private BorderPane borderPane;
    private BorderPane header;
    private Text scoreTxt;
    private Text lifesTxt;
    private Text gameOverText;

    private UIManager() {
        borderPane = new BorderPane();

        header = new BorderPane();
        header.setPadding(new Insets(0, 30, 0, 30));
        scoreTxt = new Text(0, 10, "Score: 0");
        scoreTxt.setFont(Font.font(30));
        lifesTxt = new Text(0, 10, "Lifes: 0");
        lifesTxt.setFont(Font.font(30));
        header.setLeft(scoreTxt);
        header.setRight(lifesTxt);
        borderPane.setTop(header);

        gameStackPane = new StackPane();
        gameOverText = new Text(0, 0, "Game Over");
        gameOverText.setFill(Paint.valueOf("ff0000"));
        gameOverText.setFont(Font.font(48));
        gameOverText.setVisible(false);
        gameStackPane.getChildren().add(gameOverText);
        borderPane.setCenter(gameStackPane);
    }

    public void update() {
        scoreTxt.setText("Score: " + Game.getInstance().getScore());
        lifesTxt.setText("Lifes: " + Game.getInstance().getLifes());
    }

    public void setGameOverVisible(boolean visible) {
        gameOverText.setVisible(visible);
    }

    public void setRoot(Group root) {
        root.getChildren().add(borderPane);
    }

    public void setCanvas(Canvas canvas) {
        gameStackPane.getChildren().add(0, canvas);
    }
}
