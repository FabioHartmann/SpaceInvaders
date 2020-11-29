package src.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.AudioManager;
import src.Game;
import src.Score;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private static UIManager instance;

    public static UIManager getInstance() {
        if (instance == null) instance = new UIManager();
        return instance;
    }

    private StackPane gameStackPane;
    private BorderPane borderPane;
    private HeaderUI headerUI;
    private ConfigUI configUI;
    private ObservableList<Score> scores;
    private GameOverUI gameOverUI;

    private UIManager() {
        scores = FXCollections.observableList(new ArrayList<>());

        gameStackPane = new StackPane();
        borderPane = new BorderPane();

        configUI = new ConfigUI();
        gameOverUI = new GameOverUI(scores);
        headerUI = new HeaderUI(configUI);
        borderPane.setTop(headerUI);

        gameStackPane.getChildren().add(gameOverUI);
        gameStackPane.getChildren().add(configUI);
        borderPane.setCenter(gameStackPane);
    }

    public void setScores(List<Score> scores) {
        this.scores.setAll(scores);
    }

    public void Update(long currentNanoTime, long deltaTime) {
        headerUI.Update(currentNanoTime, deltaTime);
        gameOverUI.Update(currentNanoTime, deltaTime);
    }

    public void setGameOverVisible(boolean visible) {
        gameOverUI.setVisible(visible);
    }

    public void setRoot(Group root) {
        root.getChildren().add(borderPane);
    }

    public void setCanvas(Canvas canvas) {
        gameStackPane.getChildren().add(0, canvas);
    }
}
